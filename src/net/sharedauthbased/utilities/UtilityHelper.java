/*     */ package net.sharedauthbased.utilities;
/*     */ 
/*     */ import java.io.BufferedReader;
/*     */ import java.io.File;
/*     */ import java.io.FileInputStream;
/*     */ import java.io.FileNotFoundException;
/*     */ import java.io.FileOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.InputStreamReader;
/*     */ import java.io.OutputStream;
/*     */ import java.io.OutputStreamWriter;
/*     */ import java.io.PrintStream;
/*     */ import java.math.BigInteger;
/*     */ import java.net.URL;
/*     */ import java.net.URLConnection;
/*     */ import java.net.URLEncoder;
/*     */ import java.security.InvalidKeyException;
/*     */ import java.security.Key;
/*     */ import java.security.NoSuchAlgorithmException;
/*     */ import java.sql.Blob;
/*     */ import java.sql.SQLException;
/*     */ import javax.crypto.BadPaddingException;
/*     */ import javax.crypto.Cipher;
/*     */ import javax.crypto.IllegalBlockSizeException;
/*     */ import javax.crypto.KeyGenerator;
/*     */ import javax.crypto.NoSuchPaddingException;
/*     */ import javax.crypto.SecretKey;
/*     */ import javax.crypto.spec.SecretKeySpec;
/*     */ import javax.sql.rowset.serial.SerialBlob;
/*     */ import javax.sql.rowset.serial.SerialException;
/*     */ import net.sharedauthbased.exception.SharedAuthBasedException;
/*     */ import net.sharedauthbased.model.eo.SharedAuthBasedSMSData;
/*     */ import net.sharedauthbased.model.eo.SharedAuthBasedUserEO;
/*     */ import org.apache.commons.codec.binary.Base64;
/*     */ import org.apache.commons.io.IOUtils;
/*     */ import org.apache.commons.lang.RandomStringUtils;
/*     */ import org.springframework.mail.MailSender;
/*     */ import org.springframework.mail.SimpleMailMessage;
/*     */ import sun.misc.BASE64Decoder;
/*     */ 
/*     */ public class UtilityHelper
/*     */ {
/*     */   public static void sendMail(MailSender mailSender, String subject, String userEmail, String emailBody)
/*     */     throws SharedAuthBasedException
/*     */   {
/*     */     try
/*     */     {
/*  57 */       SimpleMailMessage mailMessage = new SimpleMailMessage();
/*  58 */       mailMessage.setSubject(subject);
/*  59 */       mailMessage.setTo(userEmail);
/*  60 */       mailMessage.setText(emailBody);
/*  61 */       mailSender.send(mailMessage);
/*     */     }
/*     */     catch (Exception e) {
/*  64 */       throw new SharedAuthBasedException("Cannot Send Email " + e.getLocalizedMessage());
/*     */     }
/*     */   }
/*     */ 
/*     */   public static void sendSMS(SharedAuthBasedSMSData smsData, String userPhNo, String userText) throws SharedAuthBasedException
/*     */   {
/*  70 */     String rsp = "";
/*  71 */     String sendername = "WEBSMS";
/*  72 */     String routetype = "2";
/*  73 */     String retval = "";
/*     */     try
/*     */     {
/*  76 */       String data = URLEncoder.encode("username", "UTF-8") + "=" + URLEncoder.encode(smsData.getUserName(), "UTF-8");
/*  77 */       data = data + "&" + URLEncoder.encode("password", "UTF-8") + "=" + URLEncoder.encode(smsData.getUserPassword(), "UTF-8");
/*  78 */       data = data + "&" + URLEncoder.encode("mobile", "UTF-8") + "=" + URLEncoder.encode(userPhNo, "UTF-8");
/*  79 */       data = data + "&" + URLEncoder.encode("message", "UTF-8") + "=" + URLEncoder.encode(userText, "UTF-8");
/*  80 */       data = data + "&" + URLEncoder.encode("sendername", "UTF-8") + "=" + URLEncoder.encode(sendername, "UTF-8");
/*  81 */       data = data + "&" + URLEncoder.encode("routetype", "UTF-8") + "=" + URLEncoder.encode(routetype, "UTF-8");
/*     */ 
/*  84 */       URL url = new URL("http://clients.smshorizon.in/sms_api/sendsms.php");
/*  85 */       URLConnection conn = url.openConnection();
/*  86 */       conn.setDoOutput(true);
/*     */ 
/*  88 */       OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
/*  89 */       wr.write(data);
/*  90 */       wr.flush();
/*     */ 
/*  93 */       BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
/*     */       String line;
/*  95 */       while ((line = rd.readLine()) != null)
/*     */       {
/*     */      
/*  97 */         retval = retval + line;
/*     */       }
/*  99 */       wr.close();
/* 100 */       rd.close();
/*     */ 
/* 102 */       System.out.println(retval);
/* 103 */       rsp = retval;
/*     */     }
/*     */     catch (Exception e) {
/* 106 */       throw new SharedAuthBasedException("Cannot Send SMS " + e.getLocalizedMessage());
/*     */     }
/*     */   }
/*     */ 
/*     */   public static String encrypt(String password)
/*     */   {
/* 112 */     String key = "NTU12345NTU12345";
/* 113 */     Key aesKey = new SecretKeySpec(key.getBytes(), "AES");
/* 114 */     Cipher cipher = null;
/*     */     try {
/* 116 */       cipher = Cipher.getInstance("AES");
/*     */     } catch (NoSuchAlgorithmException e) {
/* 118 */       e.printStackTrace();
/*     */     } catch (NoSuchPaddingException e) {
/* 120 */       e.printStackTrace();
/*     */     }
/*     */     try {
/* 123 */       cipher.init(1, aesKey);
/*     */     } catch (InvalidKeyException e) {
/* 125 */       e.printStackTrace();
/*     */     }
/* 127 */     byte[] encrypted = null;
/*     */     try {
/* 129 */       encrypted = cipher.doFinal(password.getBytes());
/*     */     } catch (IllegalBlockSizeException e) {
/* 131 */       e.printStackTrace();
/*     */     } catch (BadPaddingException e) {
/* 133 */       e.printStackTrace();
/*     */     }
/* 135 */     byte[] converted = Base64.encodeBase64(password.getBytes());
/* 136 */     return new String(converted);
/*     */   }
/*     */ 
/*     */   public static String decrypt(String encodedPassword)
/*     */   {
/* 141 */     String key = "NTU12345NTU12345";
/* 142 */     Key aesKey = new SecretKeySpec(key.getBytes(), "AES");
/* 143 */     Cipher cipher = null;
/*     */     try {
/* 145 */       cipher = Cipher.getInstance("AES");
/*     */     } catch (NoSuchAlgorithmException e) {
/* 147 */       e.printStackTrace();
/*     */     } catch (NoSuchPaddingException e) {
/* 149 */       e.printStackTrace();
/*     */     }
/*     */     try {
/* 152 */       cipher.init(2, aesKey);
/*     */     } catch (InvalidKeyException e) {
/* 154 */       e.printStackTrace();
/*     */     }
/* 156 */     String decrypted = null;
/*     */ 
/* 164 */     decrypted = new String(Base64.decodeBase64(encodedPassword.getBytes()));
/* 165 */     return decrypted;
/*     */   }
/*     */ 
/*     */   public static String sha256Hex(String data) {
/* 169 */     return "1234567891011121";
/*     */   }
/*     */ 
/*     */   public static String encodeHexString(byte[] data) {
/* 173 */     return new String(encodeHex(data)); 
/*     */   }
/*     */ 
/*     */   public static char[] encodeHex(byte[] data) {
/* 177 */     return encodeHex(data);
/*     */   }
/*     */ 
/*     */   public static byte[] encryptFile(InputStream content, String password)
/*     */   {
/* 183 */     byte[] byteCipherText = null;
/* 184 */     InputStream inStream = null;
/* 185 */     OutputStream outStream = null;
/* 186 */     File temp = null;
/*     */     try
/*     */     {
/* 190 */       String encryptedStr = null;
/* 191 */       KeyGenerator keyGen = KeyGenerator.getInstance("AES");
/* 192 */       keyGen.init(128);
/* 193 */       String key = sha256Hex(password);
/* 194 */       SecretKey secretKey = new SecretKeySpec(key.getBytes(), "AES");
/* 195 */       temp = File.createTempFile("temp", ".tmp");
/*     */ 
/* 197 */       Cipher aesCipher = null;
/*     */       try {
/* 199 */         aesCipher = Cipher.getInstance("AES");
/*     */       } catch (NoSuchPaddingException e1) {
/* 201 */         e1.printStackTrace();
/*     */       }
/*     */       try {
/* 204 */         aesCipher.init(1, secretKey);
/*     */       } catch (InvalidKeyException e) {
/* 206 */         e.printStackTrace();
/*     */       }
/*     */       try {
/* 209 */         inStream = content;
/* 210 */         outStream = new FileOutputStream(temp);
/* 211 */         byte[] buffer = new byte[1024];
/*     */         int len;
/* 213 */         while ((len = inStream.read(buffer)) > 0)
/*     */         {
/*     */           
/* 214 */           outStream.write(aesCipher.update(buffer, 0, len));
/* 215 */           outStream.flush();
/*     */         }
/* 217 */         outStream.write(aesCipher.doFinal());
/* 218 */         inStream.close();
/* 219 */         outStream.close();
/*     */       } catch (IllegalBlockSizeException e) {
/* 221 */         e.printStackTrace();
/*     */       } catch (BadPaddingException e) {
/* 223 */         e.printStackTrace();
/*     */       }
/*     */     } catch (NoSuchAlgorithmException e) {
/* 226 */       e.printStackTrace();
/*     */     } catch (IOException e) {
/* 228 */       e.printStackTrace();
/*     */     }
/* 230 */     byte[] res = null;
/*     */     try {
/* 232 */       res = IOUtils.toByteArray(new FileInputStream(temp));
/*     */     } catch (FileNotFoundException e) {
/* 234 */       e.printStackTrace();
/*     */     } catch (IOException e) {
/* 236 */       e.printStackTrace();
/*     */     }
/* 238 */     return res;
/*     */   }
/*     */ 
/*     */   public static Blob decryptFile(Blob contents, String Password) {
/* 242 */     InputStream inStream = null;
/* 243 */     OutputStream outStream = null;
/* 244 */     Blob result = null;
/* 245 */     File temp1 = null;
/*     */     try
/*     */     {
/* 248 */       KeyGenerator keyGen = KeyGenerator.getInstance("AES");
/* 249 */       keyGen.init(128);
/* 250 */       String key = "1234567891011121";
/* 251 */       SecretKey secretKey = new SecretKeySpec(key.getBytes(), "AES");
/* 252 */       Cipher aesCipher = null;
/*     */       try {
/* 254 */         aesCipher = Cipher.getInstance("AES");
/*     */       }
/*     */       catch (NoSuchPaddingException e1) {
/* 257 */         e1.printStackTrace();
/*     */       }
/*     */       try {
/* 260 */         temp1 = File.createTempFile("temp1", ".tmp");
/* 261 */         aesCipher.init(2, secretKey);
/* 262 */         inStream = contents.getBinaryStream();
/* 263 */         outStream = new FileOutputStream(temp1);
/* 264 */         byte[] buffer = new byte[1024];
/*     */         int len;
/* 266 */         while ((len = inStream.read(buffer)) > 0)
/*     */         {
/*     */           
/* 267 */           outStream.write(aesCipher.update(buffer, 0, len));
/* 268 */           outStream.flush();
/*     */         }
/* 270 */         outStream.write(aesCipher.doFinal());
/* 271 */         inStream.close();
/* 272 */         outStream.close();
/*     */       }
/*     */       catch (InvalidKeyException e) {
/* 275 */         e.printStackTrace();
/*     */       }
/*     */       catch (IllegalBlockSizeException e) {
/* 278 */         e.printStackTrace();
/*     */       }
/*     */       catch (BadPaddingException e) {
/* 281 */         e.printStackTrace();
/*     */       }
/*     */       catch (IOException e) {
/* 284 */         e.printStackTrace();
/*     */       }
/*     */       catch (SQLException e) {
/* 287 */         e.printStackTrace();
/*     */       }
/* 289 */       byte[] byteCipherText = null;
/* 290 */       Long dataLength = null;
/*     */       try {
/* 292 */         dataLength = new Long(contents.length());
/*     */       }
/*     */       catch (SQLException e) {
/* 295 */         e.printStackTrace();
/*     */       }
/* 297 */       String data = null;
/*     */       try {
/* 299 */         data = new String(contents.getBytes(1L, (int)contents.length()));
/*     */       } catch (SQLException e1) {
/* 301 */         e1.printStackTrace();
/*     */       }
/* 303 */       byte[] decoded = null;
/*     */       try {
/* 305 */         decoded = new BASE64Decoder().decodeBuffer(data);
/*     */       } catch (IOException e) {
/* 307 */         e.printStackTrace();
/*     */       }
/*     */       try {
/* 310 */         result = new SerialBlob(IOUtils.toByteArray(new FileInputStream(temp1)));
/*     */       } catch (SerialException e) {
/* 312 */         e.printStackTrace();
/*     */       } catch (SQLException e) {
/* 314 */         e.printStackTrace();
/*     */       } catch (FileNotFoundException e) {
/* 316 */         e.printStackTrace();
/*     */       } catch (IOException e) {
/* 318 */         e.printStackTrace();
/*     */       }
/*     */     } catch (NoSuchAlgorithmException e) {
/* 321 */       e.printStackTrace();
/*     */     }
/* 323 */     return result;
/*     */   }
/*     */ 
/*     */   public static void performProxyEncryption(SharedAuthBasedUserEO a, SharedAuthBasedUserEO b) {
/* 327 */     BigInteger PK = a.getPK();
/* 328 */     int pk = PK.intValue();
/*     */ 
/* 330 */     BigInteger RK = a.getRK();
/* 331 */     BigInteger N = a.getN();
/* 332 */     BigInteger SK = a.getSK();
/* 333 */     BigInteger Cipher = a.getCipher();
/* 334 */     String Rand = RandomStringUtils.randomNumeric(4);
/* 335 */     int Rand1 = Integer.parseInt(Rand);
/* 336 */     BigInteger Random = BigInteger.valueOf(Rand1);
/* 337 */     BigInteger Newkey = Random.pow(pk).mod(N);
/*     */ 
/* 339 */     BigInteger PK1 = b.getPK();
/* 340 */     int pk1 = PK1.intValue();
/*     */ 
/* 342 */     BigInteger RK1 = b.getRK();
/* 343 */     BigInteger N1 = b.getN();
/* 344 */     BigInteger SK1 = a.getSK();
/* 345 */     BigInteger Cipher1 = b.getCipher();
/* 346 */     String Rand3 = RandomStringUtils.randomNumeric(4);
/* 347 */     int Rand2 = Integer.parseInt(Rand3);
/* 348 */     BigInteger Random1 = BigInteger.valueOf(Rand2);
/* 349 */     BigInteger Newkey1 = Random1.pow(pk1).mod(N1);
/*     */ 
/* 381 */     a.setUsersPublicKey(Newkey1);
/* 382 */     b.setUsersPublicKey(Newkey);
/*     */   }
/*     */ }

/* Location:           C:\vivek\SABnewnew\ImportedClasses\
 * Qualified Name:     net.sharedauthbased.utilities.UtilityHelper
 * JD-Core Version:    0.6.0
 */