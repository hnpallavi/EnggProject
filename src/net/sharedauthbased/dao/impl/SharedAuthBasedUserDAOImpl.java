/*     */ package net.sharedauthbased.dao.impl;
/*     */ 
/*     */ import java.math.BigInteger;
/*     */ import java.security.SecureRandom;
/*     */ import java.util.Date;
/*     */ import java.util.HashMap;
/*     */ import java.util.LinkedHashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import net.sharedauthbased.dao.SharedAuthBasedUserDAO;
/*     */ import net.sharedauthbased.exception.SharedAuthBasedException;
/*     */ import net.sharedauthbased.model.eo.SharedAuthBasedApprovedFiles;
/*     */ import net.sharedauthbased.model.eo.SharedAuthBasedDocSharedDetails;
/*     */ import net.sharedauthbased.model.eo.SharedAuthBasedDocumentEO;
/*     */ import net.sharedauthbased.model.eo.SharedAuthBasedSMSData;
/*     */ import net.sharedauthbased.model.eo.SharedAuthBasedUserEO;
/*     */ import net.sharedauthbased.utilities.UserPropertyGenerator;
/*     */ import net.sharedauthbased.utilities.UtilityHelper;
/*     */ import org.apache.commons.lang.RandomStringUtils;
/*     */ import org.hibernate.Query;
/*     */ import org.hibernate.Session;
/*     */ import org.hibernate.SessionFactory;
/*     */ import org.hibernate.Transaction;
/*     */ import org.springframework.beans.factory.annotation.Autowired;
/*     */ import org.springframework.mail.MailSender;
/*     */ import org.springframework.scheduling.annotation.Scheduled;
/*     */ import org.springframework.stereotype.Repository;
/*     */ import org.springframework.web.servlet.ModelAndView;
/*     */ 
/*     */ @Repository
/*     */ public class SharedAuthBasedUserDAOImpl
/*     */   implements SharedAuthBasedUserDAO
/*     */ {
/*     */ 
/*     */   @Autowired
/*     */   private SessionFactory sessionFactory;
/*     */ 
/*     */   @Autowired
/*     */   private SharedAuthBasedSMSData smsSender;
/*     */ 
/*     */   @Autowired
/*     */   private MailSender mailSender;
/*     */ 
/*     */   public ModelAndView createUser(HttpServletRequest request)
/*     */   {
/*  47 */     Session session = this.sessionFactory.openSession();
/*  48 */     String userName = request.getParameter("userName");
/*  49 */     String userEmail = request.getParameter("userEmail");
/*  50 */     String userPhone = request.getParameter("userPh");
/*  51 */     String userId = UserPropertyGenerator.getNewUserId(session);
/*  52 */     String userPassword = UserPropertyGenerator.getNewPassword();
/*     */ 
/*  55 */     BigInteger one = new BigInteger("1");
/*  56 */     BigInteger PK = new BigInteger("7");
/*  57 */     SecureRandom random = new SecureRandom();
/*     */ 
/*  59 */     BigInteger P = BigInteger.probablePrime(4, random);
/*  60 */     BigInteger Q = BigInteger.probablePrime(4, random);
/*  61 */     BigInteger N = P.multiply(Q);
/*  62 */     BigInteger PhiN = P.subtract(one).multiply(Q.subtract(one));
/*  63 */     BigInteger SK = PK.modInverse(PhiN);
/*  64 */     BigInteger RK = PK.multiply(SK);
/*  65 */     int pk = PK.intValue();
/*     */ 
/*  67 */     String Rand = RandomStringUtils.randomNumeric(4);
/*  68 */     int Rand1 = Integer.parseInt(Rand);
/*  69 */     BigInteger Random = BigInteger.valueOf(Rand1);
/*  70 */     BigInteger Cipher = Random.pow(pk).mod(N);
/*     */ 
/*  74 */     SharedAuthBasedUserEO user = new SharedAuthBasedUserEO();
/*     */ 
/*  76 */     user.setUserCreatedDate(new Date());
/*  77 */     user.setUserEmail(userEmail);
/*  78 */     user.setUserId(userId);
/*  79 */     user.setUserName(userName);
/*  80 */     user.setUserIsFirstLogin(Boolean.valueOf(true));
/*  81 */     user.setUserPassword(userPassword);
/*  82 */     user.setUserPhone(userPhone);
/*  83 */     user.setN(N);
/*  84 */     user.setPhiN(PhiN);
/*  85 */     user.setSK(SK);
/*  86 */     user.setRK(RK); 
              user.setPK(PK);
/*  87 */     user.setCipher(Cipher);
/*     */ 
/*  90 */     session.beginTransaction();
/*  91 */     session.save(user);
/*  92 */     StringBuilder sb = new StringBuilder();
/*  93 */     boolean hasErrorOccured = false;
/*  94 */     sb.append("User Created Successfully");
/*  95 */     sb.append("\n\n\n");
/*  96 */     sb.append("Your login Details");
/*  97 */     sb.append("\n\n");
/*  98 */     sb.append("UserId : " + user.getUserId());
/*  99 */     sb.append("\n\n");
/* 100 */     sb.append("Password : " + user.getUserPassword());
/* 101 */     String subject = "SAB user created successfully";
/*     */     try {
/* 103 */       UtilityHelper.sendMail(this.mailSender, subject, userEmail, sb.toString());
/* 104 */       UtilityHelper.sendSMS(this.smsSender, userPhone, sb.toString());
/*     */     }
/*     */     catch (SharedAuthBasedException e) {
/* 107 */       hasErrorOccured = true;
/* 108 */       ModelAndView localModelAndView = new ModelAndView("error", "errorMessage", e.getMessage());
/*     */       return localModelAndView;
/*     */     }
/*     */     catch (Exception e) {
/* 111 */       hasErrorOccured = true;
/* 112 */       ModelAndView localModelAndView = new ModelAndView("index", "errorMessage", e.getMessage());
/*     */       return localModelAndView;
/*     */     }
/*     */     finally {
/* 115 */       if (!hasErrorOccured) {
/* 116 */         session.getTransaction().commit();
/*     */       }
/* 118 */       session.close();
/*     */     }
/* 115 */     
/*     */ 
/* 120 */     return new ModelAndView("index", "successMessage", "User Created Successfully");
/*     */   }
/*     */ 
/*     */   public ModelAndView doLogin(HttpServletRequest request)
/*     */   {
/* 125 */     String userId = request.getParameter("userId");
/* 126 */     Map results = new LinkedHashMap();
/* 127 */     String userPassword = request.getParameter("userPassword");
/* 128 */     Session session = this.sessionFactory.openSession();
/* 129 */     Query query = session.createQuery("from SharedAuthBasedUserEO where userId=:userId");
/* 130 */     query.setParameter("userId", userId);
/* 131 */     SharedAuthBasedUserEO user = (SharedAuthBasedUserEO)query.uniqueResult();
/* 132 */     if (user == null) {
/* 133 */       return new ModelAndView("index", "errorMessage", "Login failed");
/*     */     }
/* 135 */     if (!user.getUserPassword().equals(userPassword)) {
/* 136 */       return new ModelAndView("index", "errorMessage", "Login failed");
/*     */     }
/* 138 */     if (user.getUserIsFirstLogin().booleanValue()) {
/* 139 */       results.put("userId", userId);
/* 140 */       return new ModelAndView("updatePassword", results);
/*     */     }
/* 142 */     if (user.getUserId().equals("ADMIN")) {
/* 143 */       results.put("userId", "ADMIN");
/* 144 */       results.put("userName", "ADMIN");
/* 145 */       results.put("files", getToApproveShareDetails());
/* 146 */       results.put("data", getGraphData());
/* 147 */       return new ModelAndView("loginAdmin", results);
/*     */     }
/* 149 */     results.put("userId", user.getUserId());
/* 150 */     results.put("userName", user.getUserName());
/* 151 */     results.put("users", getUsers(userId));
/* 152 */     results.put("files", getFilesToDownload(userId));
/* 153 */     results.put("files1", getFilesToRequest(userId));
/* 154 */     results.put("files2", getApprovedFilesForUser(userId));
/* 155 */     return new ModelAndView("login", results);
/*     */   }
/*     */ 
/*     */   public ModelAndView forgotPassword(HttpServletRequest request)
/*     */   {
/* 160 */     String userId = request.getParameter("userId");
/* 161 */     Session session = this.sessionFactory.openSession();
/* 162 */     Query query = session.createQuery("from SharedAuthBasedUserEO where userId=:userId");
/* 163 */     query.setParameter("userId", userId);
/* 164 */     SharedAuthBasedUserEO user = (SharedAuthBasedUserEO)query.uniqueResult();
/* 165 */     StringBuilder sb = new StringBuilder();
/* 166 */     sb.append("Password retrieved Successfully");
/* 167 */     sb.append("\n\n\n");
/* 168 */     sb.append("Your login Details");
/* 169 */     sb.append("\n\n");
/* 170 */     sb.append("UserId : " + user.getUserId());
/* 171 */     sb.append("\n\n");
/* 172 */     sb.append("Password : " + user.getUserPassword());
/* 173 */     String subject = "SAB user retrieved successfully";
/*     */     try {
/* 175 */       UtilityHelper.sendMail(this.mailSender, subject, user.getUserEmail(), sb.toString());
/* 176 */       UtilityHelper.sendSMS(this.smsSender, user.getUserPhone(), sb.toString());
/*     */     }
/*     */     catch (SharedAuthBasedException e) {
/* 179 */       return new ModelAndView("error", "errorMessage", e.getMessage());
/*     */     }
/*     */     catch (Exception e) {
/* 182 */       return new ModelAndView("index", "errorMessage", e.getMessage());
/*     */     }
/* 184 */     session.close();
/* 185 */     return new ModelAndView("index", "successMessage", "Password sent to your email");
/*     */   }
/*     */ 
/*     */   public ModelAndView updatePassword(HttpServletRequest request)
/*     */   {
/* 190 */     Session session = this.sessionFactory.openSession();
/* 191 */     String userId = request.getParameter("userId");
/* 192 */     String updatedPassword = request.getParameter("updatedPassword");
/* 193 */     Query query = session.createQuery("from SharedAuthBasedUserEO where userId=:userId");
/* 194 */     query.setParameter("userId", userId);
/* 195 */     SharedAuthBasedUserEO user = (SharedAuthBasedUserEO)query.uniqueResult();
/* 196 */     user.setUserPassword(updatedPassword);
/* 197 */     user.setUserIsFirstLogin(Boolean.valueOf(false));
/* 198 */     session.beginTransaction();
/* 199 */     session.save(user);
/* 200 */     session.getTransaction().commit();
/* 201 */     session.close();
/* 202 */     return new ModelAndView("index", "successMessage", "Password updated successfully");
/*     */   }
/*     */ 
/*     */   public ModelAndView uploadFile(SharedAuthBasedDocumentEO doc, HttpServletRequest request)
/*     */   {
/* 207 */     Session session = this.sessionFactory.openSession();
/* 208 */     session.beginTransaction();
/* 209 */     session.save(doc);
/* 210 */     session.getTransaction().commit();
/* 211 */     session.close();
/* 212 */     SharedAuthBasedUserEO user = getUserById(doc.getDocumentOwner());
/* 213 */     StringBuilder sb = new StringBuilder();
/* 214 */     sb.append("File uploaded Successfully");
/* 215 */     sb.append("\n\n\n");
/* 216 */     sb.append("Your file details");
/* 217 */     sb.append("\n\n");
/* 218 */     sb.append("File Name : " + doc.getDocumentName());
/* 219 */     sb.append("\n\n");
/* 220 */     sb.append("Password : " + UtilityHelper.decrypt(doc.getDocumentKey()));
              sb.append("\n\n");
/* 221 */     sb.append("Random number : " + doc.getKey());
/* 222 */     String subject = "SAB file uploaded successfully";
/*     */     try {
/* 224 */       UtilityHelper.sendMail(this.mailSender, subject, user.getUserEmail(), sb.toString());
/* 225 */       UtilityHelper.sendSMS(this.smsSender, user.getUserPhone(), sb.toString());
/*     */     }
/*     */     catch (SharedAuthBasedException e) {
/* 228 */       return new ModelAndView("error", "errorMessage", e.getMessage());
/*     */     }
/*     */     catch (Exception e) {
/* 231 */       return new ModelAndView("index", "errorMessage", e.getMessage());
/*     */     }
/* 233 */     Map results = new LinkedHashMap();
/* 234 */     results.put("userId", request.getParameter("userId"));
/* 235 */     results.put("userName", request.getParameter("userName"));
/* 236 */     results.put("users", getUsers(user.getUserId()));
/* 237 */     results.put("files", getFilesToDownload(user.getUserId()));
/* 238 */     results.put("files1", getFilesToRequest(user.getUserId()));
/* 239 */     results.put("files2", getApprovedFilesForUser(user.getUserId()));
/* 240 */     results.put("successMessage", "File uploaded successfully");
/* 241 */     return new ModelAndView("login", results);
/*     */   }
/*     */ 
/*     */   private Map<String, String> getUsers(String userId) {
/* 245 */     Map map = new HashMap();
/* 246 */     Session session = this.sessionFactory.openSession();
/* 247 */     Query query = session.createQuery("from SharedAuthBasedUserEO");
/* 248 */     List<SharedAuthBasedUserEO>  users = query.list();
/* 249 */     for (SharedAuthBasedUserEO user : users) {
/* 250 */       if ((user.getUserId().equals("ADMIN")) || (user.getUserId().equals(userId))) {
/*     */         continue;
/*     */       }
/* 253 */       List sourceDetails = getShareDetails(userId, user.getUserId());
/* 254 */       List destinationDetails = getShareDetails(user.getUserId(), userId);
/* 255 */       if (sourceDetails.isEmpty()) {
/* 256 */         map.put(user.getUserId(), user.getUserName());
/*     */       }
/*     */     }
/* 259 */     session.close();
/* 260 */     return map;
/*     */   }
/*     */ 
/*     */   private List<SharedAuthBasedDocSharedDetails> getShareDetails(String userId, String userId2) {
/* 264 */     Session session = this.sessionFactory.openSession();
/* 265 */     Query query = session.createQuery("from SharedAuthBasedDocSharedDetails where sourceOwner=:userId and destinationOwner=:userId2");
/* 266 */     query.setParameter("userId", userId);
/* 267 */     query.setParameter("userId2", userId2);
/* 268 */     List details = query.list();
/* 269 */     session.close();
/* 270 */     return details;
/*     */   }
/*     */ 
/*     */   private Map<String, String> getFilesToDownload(String user) {
/* 274 */     Map map = new HashMap();
/* 275 */     Session session = this.sessionFactory.openSession();
/* 276 */     Query query = session.createQuery("from SharedAuthBasedDocumentEO where documentOwner=:user");
/* 277 */     query.setParameter("user", user);
/* 278 */     List<SharedAuthBasedDocumentEO> docs = query.list();
/* 279 */     for (SharedAuthBasedDocumentEO doc : docs) {
/* 280 */       map.put(doc.getDocumentId().toString(), doc.getDocumentName());
/*     */     }
/* 282 */     session.close();
/* 283 */     return map;
/*     */   }
/*     */ 
/*     */   private SharedAuthBasedUserEO getUserById(String id) {
/* 287 */     Session session = this.sessionFactory.openSession();
/* 288 */     Query query = session.createQuery("from SharedAuthBasedUserEO where userId=:id");
/* 289 */     query.setParameter("id", id);
/* 290 */     SharedAuthBasedUserEO user = (SharedAuthBasedUserEO)query.uniqueResult();
/* 291 */     session.close();
/* 292 */     return user;
/*     */   }
/*     */ 
/*     */   private SharedAuthBasedDocumentEO getFileById(String id) {
/* 296 */     Session session = this.sessionFactory.openSession();
/* 297 */     Query query = session.createQuery("from SharedAuthBasedDocumentEO where documentId=:id");
/* 298 */     query.setParameter("id", Long.valueOf(Long.parseLong(id)));
/* 299 */     SharedAuthBasedDocumentEO docs = (SharedAuthBasedDocumentEO)query.uniqueResult();
/* 300 */     session.close();
/* 301 */     return docs;
/*     */   }
/*     */ 
/*     */   public Map getResults(String userId)
/*     */   {
/* 306 */     Map results = new HashMap();
/* 307 */     results.put("files", getFilesToDownload(userId));
/* 308 */     results.put("files1", getFilesToRequest(userId));
/* 309 */     results.put("files2", getApprovedFilesForUser(userId));
/* 310 */     results.put("users", getUsers(userId));
/* 311 */     return results;
/*     */   }
/*     */ 
/*     */   public SharedAuthBasedDocumentEO getFileToDownload(String documentId, String privateKey)
/*     */   {
/* 316 */     SharedAuthBasedDocumentEO doc = getFileById(documentId);
/*     */ 
/* 318 */     if (!UtilityHelper.decrypt(doc.getDocumentKey()).equals(privateKey)) {
/* 319 */       return null;
/*     */     }
/* 321 */     doc.setDocumentContents(UtilityHelper.decryptFile(doc.getDocumentContents(), privateKey));
/* 322 */     return doc;
/*     */   }
/*     */ 
/*     */   public Object shareRequest(String sourceId, String destId)
/*     */   {
/* 327 */     Session session = this.sessionFactory.openSession();
/* 328 */     SharedAuthBasedDocSharedDetails details = new SharedAuthBasedDocSharedDetails();
/* 329 */     details.setSourceOwner(sourceId);
/* 330 */     details.setDestinationOwner(destId);
/* 331 */     details.setStatus("PENDING");
/* 332 */     UtilityHelper.performProxyEncryption(getUserById(sourceId), getUserById(destId));
/* 333 */     session.beginTransaction();
/* 334 */     session.save(details);
/* 335 */     session.getTransaction().commit();
/* 336 */     session.close();
/* 337 */     return null;
/*     */   }
/*     */ 
/*     */   private Map<String, String> getToApproveShareDetails() {
/* 341 */     Map result = new HashMap();
/* 342 */     Session session = this.sessionFactory.openSession();
/* 343 */     Query query = session.createQuery("from SharedAuthBasedDocSharedDetails where status='PENDING'");
/* 344 */     List<SharedAuthBasedDocSharedDetails> details = query.list();
/* 345 */     for (SharedAuthBasedDocSharedDetails detail : details) {
/* 346 */       result.put(detail.getShareId().toString(), detail.getSourceOwner() + "-->" + detail.getDestinationOwner());
/*     */     }
/* 348 */     session.close();
/* 349 */     return result;
/*     */   }
/*     */ 
/*     */   public ModelAndView approveShare(HttpServletRequest request)
/*     */   {
/* 354 */     String requestId = request.getParameter("downloadFiles");
/* 355 */     Session session = this.sessionFactory.openSession();
/* 356 */     Query query = session.createQuery("from SharedAuthBasedDocSharedDetails where shareId=:id");
/* 357 */     query.setParameter("id", Long.valueOf(Long.parseLong(requestId)));
/* 358 */     SharedAuthBasedDocSharedDetails detail = (SharedAuthBasedDocSharedDetails)query.uniqueResult();
/* 359 */     detail.setStatus("APPROVED");
/* 360 */     session.beginTransaction();
/* 361 */     session.saveOrUpdate(detail);
/* 362 */     session.getTransaction().commit();
/* 363 */     session.close();
/* 364 */     Map results = new HashMap();
/* 365 */     results.put("successMessage", "Share details approved");
/* 366 */     results.put("files", getToApproveShareDetails());
/* 367 */     results.put("data", getGraphData());
/* 368 */     results.put("userId", "ADMIN");
/* 369 */     results.put("userName", "ADMIN");
/* 370 */     return new ModelAndView("loginAdmin", results);
/*     */   }
/*     */ 
/*     */   private Map<String, String> getFilesToRequest(String userId) {
/* 374 */     Map map = new HashMap();
/* 375 */     Session session = this.sessionFactory.openSession();
/* 376 */     Query query = session.createQuery("from SharedAuthBasedUserEO");
/* 377 */     List<SharedAuthBasedUserEO> users = query.list();
/* 378 */     for (SharedAuthBasedUserEO user : users) {
/* 379 */       if ((user.getUserId().equals("ADMIN")) || (user.getUserId().equals(userId))) {
/*     */         continue;
/*     */       }
/* 382 */       List sourceDetails = getShareDetails(userId, user.getUserId());
/* 383 */       List destinationDetails = getShareDetails(user.getUserId(), userId);
/* 384 */       if ((!sourceDetails.isEmpty()) && (!destinationDetails.isEmpty())) {
/* 385 */         List<SharedAuthBasedDocumentEO> docs = getFilesForUser(user.getUserId());
/* 386 */         for (SharedAuthBasedDocumentEO doc : docs) {
/* 387 */           SharedAuthBasedApprovedFiles files = getSharedAprovedFilesForUser(userId);
/* 388 */           if ((files != null) && (!files.getListOfDocuments().isEmpty()) && (files.getListOfDocuments().contains(doc.getDocumentId()))) {
/*     */             continue;
/*     */           }
/* 391 */           map.put(doc.getDocumentId().toString(), user.getUserName().concat("::").concat(doc.getDocumentName()));
/*     */         }
/*     */       }
/*     */     }
/* 395 */     session.close();
/* 396 */     return map;
/*     */   }
/*     */ 
/*     */   private List<SharedAuthBasedDocumentEO> getFilesForUser(String userId) {
/* 400 */     Session session = this.sessionFactory.openSession();
/* 401 */     Query query = session.createQuery("from SharedAuthBasedDocumentEO where documentOwner=:user");
/* 402 */     query.setParameter("user", userId);
/* 403 */     List docs = query.list();
/* 404 */     session.close();
/* 405 */     return docs;
/*     */   }
/*     */ 
/*     */   public ModelAndView requestForDocument(HttpServletRequest request)
/*     */   {
/* 410 */     String docId = request.getParameter("docs");
/* 411 */     String userId = request.getParameter("userId");
/* 412 */     String userName = request.getParameter("userName");
/* 413 */     SharedAuthBasedApprovedFiles approvedFiles = getSharedAprovedFilesForUser(userId);
/* 414 */     if (approvedFiles == null) {
/* 415 */       approvedFiles = new SharedAuthBasedApprovedFiles();
/* 416 */       approvedFiles.setSharedOwner(userId);
/* 417 */       approvedFiles.getListOfDocuments().add(Long.valueOf(Long.parseLong(docId)));
/*     */     }
/*     */     else {
/* 420 */       approvedFiles.getListOfDocuments().add(Long.valueOf(Long.parseLong(docId)));
/*     */     }
/* 422 */     Session session = this.sessionFactory.openSession();
/* 423 */     session.beginTransaction();
/* 424 */     session.saveOrUpdate(approvedFiles);
/* 425 */     session.getTransaction().commit();
/* 426 */     session.close();
/* 427 */     StringBuilder sb = new StringBuilder();
/* 428 */     SharedAuthBasedUserEO user = getUserById(userId);
/* 429 */     SharedAuthBasedDocumentEO doc = getFileById(docId);
/*     */ 
/* 432 */     BigInteger PK = user.getPK();
/* 433 */     BigInteger SK = user.getSK();
/* 434 */     int pk = PK.intValue();
/* 435 */     int sk = SK.intValue();
/* 436 */     BigInteger RK = user.getRK();
/* 437 */     BigInteger N = user.getN();
/* 438 */     BigInteger Cipher = user.getCipher();
/* 439 */     String Rand = RandomStringUtils.randomNumeric(4);
/* 440 */     int Rand1 = Integer.parseInt(Rand);
/* 441 */     BigInteger Random = BigInteger.valueOf(Rand1);
/* 442 */     BigInteger Newkey = Random.pow(pk).mod(N);
/* 443 */     BigInteger Newkey1 = Newkey.pow(sk).mod(N);
/*     */ 
/* 446 */     sb.append("File shared Successfully");
/* 447 */     sb.append("\n\n\n");
/* 448 */     sb.append("Your file details");
/* 449 */     sb.append("\n\n");
/* 450 */     sb.append("File Name : " + doc.getDocumentName());
/* 451 */     sb.append("\n\n");
/*     */ 
/* 453 */     /*sb.append("Random Number : " + user.getUsersPublicKey());
/* 454 */     /*sb.append("\n\n");
/*     */ 
/* 457 */     /* sb.append("Users reencrypted key : " + user.getUsersPublicKey());
/* 458 */     /*sb.append("\n\n");
/*     */ 
/* 461 */     sb.append("Password : " + UtilityHelper.decrypt(doc.getDocumentKey()));
/* 462 */     String subject = "SAB file shared successfully";
/*     */     try {
/* 464 */       UtilityHelper.sendMail(this.mailSender, subject, user.getUserEmail(), sb.toString());
/* 465 */       UtilityHelper.sendSMS(this.smsSender, user.getUserPhone(), sb.toString());
/*     */     }
/*     */     catch (SharedAuthBasedException e) {
/* 468 */       return new ModelAndView("error", "errorMessage", e.getMessage());
/*     */     }
/* 470 */     Map results = new HashMap();
/* 471 */     results.put("userId", user.getUserId());
/* 472 */     results.put("userName", user.getUserName());
/* 473 */     results.put("users", getUsers(userId));
/* 474 */     results.put("files", getFilesToDownload(userId));
/* 475 */     results.put("files1", getFilesToRequest(userId));
/* 476 */     results.put("files2", getApprovedFilesForUser(userId));
/* 477 */     return new ModelAndView("login", results);
/*     */   }
/*     */ 
/*     */   private SharedAuthBasedApprovedFiles getSharedAprovedFilesForUser(String userId)
/*     */   {
/* 482 */     Session session = this.sessionFactory.openSession();
/* 483 */     Query query = session.createQuery("from SharedAuthBasedApprovedFiles where sharedOwner=:userId");
/* 484 */     query.setParameter("userId", userId);
/* 485 */     SharedAuthBasedApprovedFiles files = (SharedAuthBasedApprovedFiles)query.uniqueResult();
/* 486 */     session.close();
/* 487 */     return files;
/*     */   }
/*     */ 
/*     */   private Map<String, String> getApprovedFilesForUser(String userId) {
/* 491 */     Map maps = new HashMap();
/* 492 */     SharedAuthBasedApprovedFiles files = getSharedAprovedFilesForUser(userId);
/* 493 */     if (files != null) {
/* 494 */       for (Long doc : files.getListOfDocuments()) {
/* 495 */         SharedAuthBasedDocumentEO docs = getFileById(doc.toString());
/* 496 */         maps.put(docs.getDocumentId().toString(), docs.getDocumentName());
/*     */       }
/*     */     }
/* 499 */     return maps;
/*     */   }
/*     */ 
/*     */   public String getGraphData()
/*     */   {
/* 504 */     StringBuilder sb = new StringBuilder();
/* 505 */     Session session = this.sessionFactory.openSession();
/* 506 */     Query query = session.createQuery("from SharedAuthBasedUserEO");
/* 507 */     List<SharedAuthBasedUserEO> users = query.list();
/* 508 */     String labels = new String();
/* 509 */     String values = new String();
/* 510 */     for (SharedAuthBasedUserEO user : users) {
/* 511 */       labels = labels + "'";
/* 512 */       labels = labels + user.getUserName();
/* 513 */       labels = labels + "'";
/* 514 */       labels = labels + ",";
/* 515 */       int size = getFilesForUser(user.getUserId()).size();
/* 516 */       values = values + size;
/* 517 */       values = values + ",";
/*     */     }
/* 519 */     if ((labels == null) || (values.length() == 0)) {
/* 520 */       return null;
/*     */     }
/* 522 */     String data = "{labels : [" + labels.substring(0, labels.length() - 1) + "],";
/* 523 */     data = data + "datasets : [{";
/* 524 */     data = data + "fillColor : \"rgba(151,187,205,0.5)\",strokeColor : \"rgba(151,187,205,1)\",data : [" + values.substring(0, values.length() - 1) + "]}]}";
              session.close();
/* 525 */     return data;
/*     */   }
/*     */   @Scheduled(fixedDelay=6000L)
/*     */   public void createDefaultUser() {
/* 530 */     Session session = this.sessionFactory.openSession();
/* 531 */     Query query = session.createQuery("from SharedAuthBasedUserEO where userId=:userId");
/* 532 */     query.setParameter("userId", "ADMIN");
/* 533 */     SharedAuthBasedUserEO user = (SharedAuthBasedUserEO)query.uniqueResult();
/*     */ 
/* 535 */     Query query1 = session.createQuery("from SharedAuthBasedUserEO where userId=:userId");
/* 536 */     query1.setParameter("userId", "ANALYSIS");
/* 537 */     SharedAuthBasedUserEO user1 = (SharedAuthBasedUserEO)query1.uniqueResult();
/*     */ 
/* 539 */     if (user == null) {
/* 540 */       user = new SharedAuthBasedUserEO();
/* 541 */       user.setUserCreatedDate(new Date());
/* 542 */       user.setUserEmail("hnpallavi94@gmail.com");
/* 543 */       user.setUserId("ADMIN");
/* 544 */       user.setUserIsFirstLogin(Boolean.valueOf(true));
/* 545 */       user.setUserName("ADMIN");
/* 546 */       user.setUserPassword("pwd");
/* 547 */       user.setUserPhone("8867522521");
/* 548 */       session.beginTransaction();
/* 549 */       session.save(user);
/* 550 */       session.getTransaction().commit();
/*     */     }
/*     */ 
/* 553 */     if (user1 == null) {
/* 554 */       user1 = new SharedAuthBasedUserEO();
/* 555 */       user1.setUserCreatedDate(new Date());
/* 556 */       user1.setUserEmail("hnpallavi94@gmail.com");
/* 557 */       user1.setUserId("ANALYSIS");
/* 558 */       user1.setUserIsFirstLogin(Boolean.valueOf(true));
/* 559 */       user1.setUserName("ANALYSIS");
/* 560 */       user1.setUserPassword("pwd");
/* 561 */       user1.setUserPhone("8867522521");
/* 562 */       session.beginTransaction();
/* 563 */       session.save(user1);
/* 564 */       session.getTransaction().commit();
/*     */     }
/* 566 */     session.close();
/*     */   }
/*     */ }

/* Location:           C:\vivek\SABnewnew\ImportedClasses\
 * Qualified Name:     net.sharedauthbased.dao.impl.SharedAuthBasedUserDAOImpl
 * JD-Core Version:    0.6.0
 */