/*
 * Decompiled with CFR 0_115.
 * 
 * Could not load the following classes:
 *  com.lambdaworks.crypto.SCryptUtil
 *  util.Base64
 *  util.Constants
 *  util.ServerCrypt
 */
package util;

//import com.lambdaworks.crypto.SCryptUtil;
import com.lambdaworks.crypto.SCryptUtil;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.security.GeneralSecurityException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.KeyStore;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.Signature;
import java.security.SignatureException;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.ECGenParameterSpec;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SealedObject;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import util.Base64;

/*
 * Exception performing whole class analysis ignored.
 */
public class ServerCrypt {
    private final String iv = "fe5c8a9w7s5d321a";
    private IvParameterSpec ivspec = new IvParameterSpec("fe5c8a9w7s5d321a".getBytes());
    private SecretKeySpec keyspec = new SecretKeySpec("a123d5s7w9a8c5ef".getBytes(), "AES");
    private Cipher cipher;
    private final String secretKey = "a123d5s7w9a8c5ef";
    private static KeyPair keyPair;
    public static String ECPVKey;
    public static String ECPBKey;

    public static String getBase64InitialPrivateKey() {
        try {
            KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA");
            kpg.initialize(2048);
            KeyPair kp = kpg.generateKeyPair();
            PrivateKey privateKey = kp.getPrivate();
            String result = Base64.encodeToString((byte[])privateKey.getEncoded(), (int)0);
            return result;
        }
        catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }

    public ServerCrypt() {
        try {
            this.cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        }
        catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        catch (NoSuchPaddingException e) {
            e.printStackTrace();
        }
    }

    public static String getBase64InitialPublicKey() {
        try {
            KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA");
            kpg.initialize(2048);
            KeyPair kp = kpg.generateKeyPair();
            PublicKey publicKey = kp.getPublic();
            String result = Base64.encodeToString((byte[])publicKey.getEncoded(), (int)0);
            return result;
        }
        catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static String getKey(String filename) throws IOException {
        String line;
        String strKeyPEM = "";
        BufferedReader br = new BufferedReader(new FileReader(filename));
        while ((line = br.readLine()) != null) {
            strKeyPEM = strKeyPEM + line + "\n";
        }
        br.close();
        return strKeyPEM;
    }

    public static RSAPrivateKey getPrivateKey(String filename) throws IOException, GeneralSecurityException {
        String privateKeyPEM = ServerCrypt.getKey(filename);
        return ServerCrypt.getPrivateKeyFromString(privateKeyPEM);
    }

    public static RSAPrivateKey getPrivateKeyFromString(String key) throws IOException, GeneralSecurityException {
        String privateKeyPEM = key;
        privateKeyPEM = privateKeyPEM.replace("-----BEGIN PRIVATE KEY-----\n", "");
        privateKeyPEM = privateKeyPEM.replace("-----END PRIVATE KEY-----", "");
        byte[] encoded = Base64.decode(privateKeyPEM, (int)0);
        KeyFactory kf = KeyFactory.getInstance("RSA");
        RSAPrivateKey privKey = (RSAPrivateKey)kf.generatePrivate(new PKCS8EncodedKeySpec(encoded));
        return privKey;
    }

    public static RSAPublicKey getPublicKey(String filename) throws IOException, GeneralSecurityException {
        String publicKeyPEM = ServerCrypt.getKey(filename);
        return ServerCrypt.getPublicKeyFromString(publicKeyPEM);
    }

    public static RSAPublicKey getPublicKeyFromString(String key) throws IOException, GeneralSecurityException {
        String publicKeyPEM = key;
        publicKeyPEM = publicKeyPEM.replace("-----BEGIN PUBLIC KEY-----\n", "");
        publicKeyPEM = publicKeyPEM.replace("-----END PUBLIC KEY-----", "");
        byte[] encoded = Base64.decode(publicKeyPEM, (int)0);
        KeyFactory kf = KeyFactory.getInstance("RSA");
        RSAPublicKey pubKey = (RSAPublicKey)kf.generatePublic(new X509EncodedKeySpec(encoded));
        return pubKey;
    }

    public static PublicKey getECPublicKeyFromString(String key) throws IOException, GeneralSecurityException {
        String publicKeyPEM = key;
        publicKeyPEM = publicKeyPEM.replace("-----BEGIN PUBLIC KEY-----\n", "");
        publicKeyPEM = publicKeyPEM.replace("-----END PUBLIC KEY-----", "");
        byte[] encoded = Base64.decode(publicKeyPEM, (int)0);
        KeyFactory kf = KeyFactory.getInstance("EC");
        PublicKey pubKey = kf.generatePublic(new X509EncodedKeySpec(encoded));
        return pubKey;
    }

    public static PrivateKey getECPrivateKeyFromString(String key) throws IOException, GeneralSecurityException {
        String privateKeyPEM = key;
        privateKeyPEM = privateKeyPEM.replace("-----BEGIN PRIVATE KEY-----\n", "");
        privateKeyPEM = privateKeyPEM.replace("-----END PRIVATE KEY-----", "");
        byte[] encoded = Base64.decode(privateKeyPEM, (int)0);
        KeyFactory kf = KeyFactory.getInstance("EC");
        PrivateKey privKey = kf.generatePrivate(new PKCS8EncodedKeySpec(encoded));
        return privKey;
    }

    public static String sign(PrivateKey privateKey, String message) throws NoSuchAlgorithmException, InvalidKeyException, SignatureException, UnsupportedEncodingException {
        Signature sign = Signature.getInstance("SHA256withRSA");
        sign.initSign(privateKey);
        sign.update(message.getBytes("UTF-8"));
        return new String(Base64.decode(new String(sign.sign()), (int)0), "UTF-8");
    }

    public static KeyPair generateKeyPair() throws Exception {
        KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA");
        generator.initialize(2048, new SecureRandom());
        KeyPair pair = generator.generateKeyPair();
        return pair;
    }

    public static boolean verify(PublicKey publicKey, String message, String signature) throws SignatureException, NoSuchAlgorithmException, UnsupportedEncodingException, InvalidKeyException {
        Signature sign = Signature.getInstance("SHA256withRSA");
        sign.initVerify(publicKey);
        sign.update(message.getBytes("UTF-8"));
        return sign.verify(Base64.decode(new String(signature.getBytes("UTF-8")), (int)0));
    }

    public static String encryptToString(String rawText, RSAPublicKey publicKey) throws IOException, GeneralSecurityException {
        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        cipher.init(1, publicKey);
        return Base64.encodeToString((byte[])cipher.doFinal(rawText.getBytes("UTF-8")), (int)0);
    }

    public static byte[] encrypt(String rawText, RSAPublicKey publicKey) throws IOException, GeneralSecurityException {
        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        cipher.init(1, publicKey);
        return cipher.doFinal(rawText.getBytes("UTF-8"));
    }

  

  

    public static String decrypt(String cipherText, PrivateKey privateKey) throws IOException, GeneralSecurityException {
        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        cipher.init(2, privateKey);
        return new String(cipher.doFinal(Base64.decode(cipherText, (int)0)), "UTF-8");
    }

    public static byte[] decrypt(String cipherText, RSAPrivateKey privateKey) throws IOException, GeneralSecurityException {
        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        cipher.init(2, privateKey);
        return cipher.doFinal(Base64.decode(cipherText, (int)0));
    }

    public static byte[] encrypt(String text, SecretKey sk) throws Exception {
        if (text == null || text.length() == 0) {
            throw new Exception("Empty string");
        }
        byte[] encrypted = null;
        try {
            SecretKeySpec sks = new SecretKeySpec(sk.getEncoded(), "AES");
            Cipher cip = Cipher.getInstance("AES");
            cip.init(1, sks);
            encrypted = cip.doFinal(ServerCrypt.padString(text).getBytes());
        }
        catch (Exception e) {
            throw new Exception("[encrypt] " + e.getMessage());
        }
        return encrypted;
    }

    public static KeyPair getECCKeyPair() {
        try {
            KeyPairGenerator kpg = KeyPairGenerator.getInstance("EC", "SunEC");
            ECGenParameterSpec ECGSpec = new ECGenParameterSpec("sect409r1");
            kpg.initialize(ECGSpec);
            keyPair = kpg.generateKeyPair();
            System.out.println("PVK " + Base64.encodeToString((byte[])keyPair.getPrivate().getEncoded(), (int)0) + "\nPBK " + Base64.encodeToString((byte[])keyPair.getPublic().getEncoded(), (int)0));
            return keyPair;
        }
        catch (Exception ex) {
            Logger.getLogger(ServerCrypt.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    public static String generateSecuredPassword(String password) {
        return SCryptUtil.scrypt(password, (int)16, (int)16, (int)16);
    }

    public static boolean checkSecuredPassword(String password, String secured) {
        return SCryptUtil.check(password, secured);
    }

    public static byte[] decrypt(byte[] code, SecretKey key) throws Exception {
        if (code == null || code.length == 0) {
            throw new Exception("Empty file");
        }
        byte[] decrypted = null;
        try {
            SecretKeySpec sks = new SecretKeySpec(key.getEncoded(), "AES");
            Cipher c = Cipher.getInstance("AES");
            c.init(2, sks);
            decrypted = c.doFinal(code);
        }
        catch (Exception e) {
            throw new Exception("[decrypt] " + e.getMessage());
        }
        return decrypted;
    }

  

    

    

    public static KeyPair getSyncKeyPairAsResource() {
        try {
            KeyStore ks = KeyStore.getInstance("JKS");
            InputStream fis = ServerCrypt.class.getResourceAsStream("/batches/kazistore.jks");
            ks.load(fis, "409-ks-3x".toCharArray());
            KeyStore.PasswordProtection keyPass = new KeyStore.PasswordProtection("409-ks-3y".toCharArray());
            KeyStore.PrivateKeyEntry privKeyEntry = (KeyStore.PrivateKeyEntry)ks.getEntry("synckey", keyPass);
            Certificate cert = ks.getCertificate("synckey");
            PrivateKey pvk = privKeyEntry.getPrivateKey();
            PublicKey pbk = cert.getPublicKey();
            return new KeyPair(pbk, pvk);
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static PrivateKey getECPrivateKey() {
        try {
            return ServerCrypt.getECPrivateKeyFromString(ECPVKey);
        }
        catch (IOException ex) {
            Logger.getLogger(ServerCrypt.class.getName()).log(Level.SEVERE, null, ex);
        }
        catch (GeneralSecurityException ex) {
            Logger.getLogger(ServerCrypt.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public static PublicKey getECPublicKey() {
        try {
            return ServerCrypt.getECPublicKeyFromString(ECPBKey);
        }
        catch (IOException ex) {
            Logger.getLogger(ServerCrypt.class.getName()).log(Level.SEVERE, null, ex);
        }
        catch (GeneralSecurityException ex) {
            Logger.getLogger(ServerCrypt.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public static void ECEncryptToFile(PublicKey pubKey, String text, String padding, String pathTarget) {
        try {
            Cipher cipher = Cipher.getInstance(padding);
            cipher.init(1, pubKey);
            FileOutputStream fos = new FileOutputStream(pathTarget);
            CipherOutputStream cos = new CipherOutputStream(fos, cipher);
            byte[] dataIn = text.getBytes();
            cos.write(dataIn, 0, dataIn.length);
            cos.flush();
            cos.close();
        }
        catch (Exception ex) {
            Logger.getLogger(ServerCrypt.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void encryptWithRSAToFile(RSAPublicKey pubKey, String text, String pathTarget) {
        try {
            byte[] data = ServerCrypt.encrypt(text, (RSAPublicKey)pubKey);
            System.out.println("text " + text + "      " + data.length);
            File f = new File(pathTarget);
            Path p = FileSystems.getDefault().getPath(pathTarget, new String[0]);
            Files.write(p, data, new OpenOption[]{null});
        }
        catch (Exception ex) {
            Logger.getLogger(ServerCrypt.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void encryptToFile(Key pubKey, String text, String pathTarget) {
        try {
            byte[] data = ServerCrypt.encrypt(text, (SecretKey)((SecretKey)pubKey));
            System.out.println("text " + text + "      " + data.length);
            Path p = FileSystems.getDefault().getPath(pathTarget, new String[0]);
            Files.write(p, data, new OpenOption[0]);
        }
        catch (Exception ex) {
            Logger.getLogger(ServerCrypt.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static String decryptFile(Key pubKey, String pathTarget) {
        try {
            Path p = FileSystems.getDefault().getPath(pathTarget, new String[0]);
            byte[] b = Files.readAllBytes(p);
            byte[] data = ServerCrypt.decrypt((byte[])b, (SecretKey)((SecretKey)pubKey));
            return new String(data, "UTF-8");
        }
        catch (Exception ex) {
            Logger.getLogger(ServerCrypt.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    public static Object dencrypt(SealedObject so, SecretKey scr) {
        try {
            SecretKeySpec sks = new SecretKeySpec(scr.getEncoded(), "AES");
            Cipher cip = Cipher.getInstance("AES");
            cip.init(2, sks);
            return so.getObject(cip);
        }
        catch (Exception e) {
            return null;
        }
    }

    public static SealedObject encrypt(Serializable ss, SecretKey scr) {
        try {
            SecretKeySpec sks = new SecretKeySpec(scr.getEncoded(), "AES");
            Cipher cip = Cipher.getInstance("AES");
            cip.init(1, sks);
            return new SealedObject(ss, cip);
        }
        catch (Exception e) {
            return null;
        }
    }




    public static void savePrivateKey(KeyStore ks, String alias, X509Certificate[] certs) {
        try {
            char[] password = "409-KS-X#".toCharArray();
            PrivateKey key = keyPair.getPrivate();
            ks.setKeyEntry(alias, key, password, certs);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String ECDecryptFile(PrivateKey privKey, String pathTarget) {
        try {
            int i;
            Cipher cipher = Cipher.getInstance("DES");
            cipher.init(2, privKey);
            FileInputStream fis = new FileInputStream(pathTarget);
            CipherInputStream cis = new CipherInputStream(fis, cipher);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            StringBuilder sb = new StringBuilder();
            byte[] data = new byte[1024];
            while ((i = cis.read(data)) != -1) {
                baos.write(data, 0, i);
                sb.append(new String(baos.toByteArray()));
            }
            return sb.toString();
        }
        catch (Exception ex) {
            Logger.getLogger(ServerCrypt.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    public static String decryptRSAFile(RSAPrivateKey privKey, String pathTarget) {
        try {
            int i;
            Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
            cipher.init(2, privKey);
            FileInputStream fis = new FileInputStream(pathTarget);
            CipherInputStream cis = new CipherInputStream(fis, cipher);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            StringBuilder sb = new StringBuilder();
            byte[] data = new byte[1024];
            while ((i = cis.read(data)) != -1) {
                baos.write(data, 0, i);
                sb.append(new String(baos.toByteArray()));
            }
            return sb.toString();
        }
        catch (Exception ex) {
            Logger.getLogger(ServerCrypt.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    public static String bytesToHex(byte[] b) {
        StringBuilder buf = new StringBuilder();
        int len = b.length;
        for (int j = 0; j < len; ++j) {
            buf.append(ServerCrypt.byteToHex((byte)b[j]));
        }
        return buf.toString();
    }

    public static String byteToHex(byte b) {
        char[] hexDigit = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
        char[] a = new char[]{hexDigit[b >> 4 & 15], hexDigit[b & 15]};
        return new String(a);
    }

    public static byte[] hexToBytes(String str) {
        if (str == null) {
            return null;
        }
        if (str.length() < 2) {
            return null;
        }
        int len = str.length() / 2;
        byte[] buffer = new byte[len];
        for (int i = 0; i < len; ++i) {
            buffer[i] = (byte)Integer.parseInt(str.substring(i * 2, i * 2 + 2), 16);
        }
        return buffer;
    }

    private static String padString(String source) {
        char paddingChar = '\u0000';
        int size = 16;
        int x = source.length() % size;
        int padLength = size - x;
        for (int i = 0; i < padLength; ++i) {
            source = source + paddingChar;
        }
        return source;
    }

    static {
        ECPVKey = "MFECAQAwEAYHKoZIzj0CAQYFK4EEACUEOjA4AgEBBDN1DVqaTLg/Amrr2gC7I33bKg3ryIAfGE4xNhssppHwEFwpQwiqXb8VrT2L28OaoG6XvhI=";
        ECPBKey = "MH4wEAYHKoZIzj0CAQYFK4EEACUDagAEAMKZqPeQzvM+ZCryJQmgI69Br/ZRxEn95720dMelV8Tn8rXNtjJHcaeWMvzFMkO+yf8jYwHxk/QXVDXxlbjQ8+OvPNFvOIu/azPXDSCFOqDlYc3lkjesWRLubjx11gZ5QFqOtNTDElY=";
    }
}

