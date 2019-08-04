/*     */ package net.sharedauthbased.controller;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.OutputStream;
/*     */ import java.math.BigInteger;
/*     */ import java.sql.Blob;
/*     */ import java.sql.SQLException;
/*     */ import java.util.Date;
/*     */ import java.util.HashMap;
/*     */ import java.util.LinkedHashMap;
/*     */ import java.util.Map;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import javax.servlet.http.HttpSession;
/*     */ import net.sharedauthbased.model.eo.SharedAuthBasedDocumentEO;
/*     */ import net.sharedauthbased.model.eo.SharedAuthBasedUserEO;
/*     */ import net.sharedauthbased.service.SharedAuthBasedUserService;
/*     */ import net.sharedauthbased.utilities.UtilityHelper;
/*     */ import org.apache.commons.io.IOUtils;
/*     */ import org.apache.commons.lang.RandomStringUtils;
/*     */ import org.hibernate.Hibernate;
/*     */ import org.springframework.beans.factory.annotation.Autowired;
/*     */ import org.springframework.stereotype.Controller;
/*     */ import org.springframework.web.bind.annotation.RequestMapping;
/*     */ import org.springframework.web.bind.annotation.RequestParam;
/*     */ import org.springframework.web.multipart.MultipartFile;
/*     */ import org.springframework.web.servlet.ModelAndView;
/*     */ 
/*     */ @Controller
/*     */ public class SharedAuthBasedController
/*     */ {
/*     */ 
/*     */   @Autowired
/*     */   private SharedAuthBasedUserService userService;
/*     */ 
/*     */   @RequestMapping(method={org.springframework.web.bind.annotation.RequestMethod.POST}, value={"/createUser.sab"})
/*     */   public ModelAndView createUser(HttpServletRequest request)
/*     */   {
/*  40 */     return this.userService.createUser(request);
/*     */   }
/*     */   @RequestMapping(method={org.springframework.web.bind.annotation.RequestMethod.POST}, value={"/doLogin.sab"})
/*     */   public ModelAndView doLogin(HttpServletRequest request) {
/*  45 */     return this.userService.doLogin(request);
/*     */   }
/*     */   @RequestMapping(method={org.springframework.web.bind.annotation.RequestMethod.POST}, value={"/forgotPassword.sab"})
/*     */   public ModelAndView forgotPassword(HttpServletRequest request) {
/*  50 */     return this.userService.forgotPassword(request);
/*     */   }
/*     */   @RequestMapping(method={org.springframework.web.bind.annotation.RequestMethod.POST}, value={"/updatePassword.sab"})
/*     */   public ModelAndView updatePassword(HttpServletRequest request) {
/*  55 */     return this.userService.updatePassword(request);
/*     */   }
/*     */ 
/*     */   @RequestMapping(method={org.springframework.web.bind.annotation.RequestMethod.POST}, value={"/upload.sab"})
/*     */   public ModelAndView uploadFile(@RequestParam("uploadedFile") MultipartFile content, HttpServletRequest request) {
/*  61 */     String userId = request.getParameter("userId");
/*  62 */     Blob blob = null;
/*  63 */     String key = request.getParameter("privateKey");
/*  64 */     String Rand = RandomStringUtils.randomNumeric(4);
/*  65 */     int Rand1 = Integer.parseInt(Rand);
/*  66 */     BigInteger Random = BigInteger.valueOf(Rand1);
/*  67 */     BigInteger PK = new BigInteger("7");
/*  68 */     SharedAuthBasedUserEO doc1 = new SharedAuthBasedUserEO();
/*  69 */     BigInteger N = doc1.getN();
			  if (null == N) {
/*  70 */       N = new BigInteger("123");
/*     */     }
/*     */ 
/*  71 */     BigInteger PhiN = doc1.getPhiN();
              if (null == PhiN) {
/*  75 */       PhiN = new BigInteger("123");
/*     */     }

/*  72 */     int pk = PK.intValue();
/*     */ 
/*  77 */     BigInteger Cipher = Random.pow(pk).mod(N);
/*     */     try {
/*  79 */       byte[] bytes = UtilityHelper.encryptFile(content.getInputStream(), key);
/*  80 */       blob = Hibernate.createBlob(bytes);
/*     */     } catch (IOException e) {
/*  82 */       e.printStackTrace();
/*     */     }
/*  84 */     SharedAuthBasedDocumentEO doc = new SharedAuthBasedDocumentEO();
/*  85 */     doc.setDocumentContents(blob);
/*  86 */     doc.setKey(Cipher);
/*  87 */     doc.setDocumentDate(new Date());
/*  88 */     doc.setDocumentOwner(userId);
/*  89 */     doc.setN(N);
/*  90 */     doc.setDocumentKey(UtilityHelper.encrypt(key));
/*  91 */     doc.setDocumentName(content.getOriginalFilename());
/*  92 */     doc.setDocumentType(content.getContentType());
/*  93 */     return this.userService.uploadFile(doc, request);
/*     */   }
/*     */   @RequestMapping(method={org.springframework.web.bind.annotation.RequestMethod.GET}, value={"/logout.sab"})
/*     */   public ModelAndView logout(HttpServletRequest request) {
/*  98 */     request.getSession().invalidate();
/*  99 */     return new ModelAndView("index", "successMessage", "Logged out successfully");
/*     */   }
/*     */   @RequestMapping(method={org.springframework.web.bind.annotation.RequestMethod.POST}, value={"/download.sab"})
/*     */   public ModelAndView downloadFile(HttpServletRequest request, HttpServletResponse response) {
/* 104 */     Map results = new LinkedHashMap();
/* 105 */     String userId = request.getParameter("userId");
/* 106 */     String documentId = request.getParameter("downloadFiles");
/* 107 */     String privateKey = request.getParameter("privateKey");
/* 108 */     String userName = request.getParameter("userName");
/* 109 */     SharedAuthBasedDocumentEO doc = this.userService.getFileToDownload(documentId, privateKey);
/* 110 */     if (doc == null) {
/* 111 */       results.put("errorMessage", "Cannot download file");
/* 112 */       Map newResult = this.userService.getResults(userId);
/* 113 */       results.putAll(newResult);
/* 114 */       return new ModelAndView("login", results);
/*     */     }
/*     */     try {
/* 117 */       response.setHeader("Content-Disposition", "attachment;filename=\"" + doc.getDocumentName() + "\"");
/* 118 */       response.setContentType(doc.getDocumentType());
/* 119 */       OutputStream out = response.getOutputStream();
/* 120 */       IOUtils.copy(doc.getDocumentContents().getBinaryStream(), out);
/* 121 */       out.flush();
/* 122 */       out.close();
/*     */     }
/*     */     catch (IOException e) {
/* 125 */       e.printStackTrace();
/*     */     } catch (SQLException e) {
/* 127 */       e.printStackTrace();
/*     */     }
/* 129 */     results.put("successMessage", "File downloaded successfully");
/* 130 */     Map newResult = this.userService.getResults(userId);
/* 131 */     results.putAll(newResult);
/* 132 */     return new ModelAndView("login", results);
/*     */   }
/*     */   @RequestMapping(method={org.springframework.web.bind.annotation.RequestMethod.POST}, value={"/share.sab"})
/*     */   public ModelAndView shareDocument(HttpServletRequest request) {
/* 137 */     String sourceId = request.getParameter("userId");
/* 138 */     String destId = request.getParameter("users");
/* 139 */     String userName = request.getParameter("userName");
/* 140 */     Map results = new HashMap();
/* 141 */     results.put("userId", sourceId);
/* 142 */     results.put("userName", userName);
/* 143 */     this.userService.shareRequest(sourceId, destId);
/* 144 */     results.put("successMessage", "Share request has been sent, you cannot share details unless the destination users shares details");
/* 145 */     Map newResult = this.userService.getResults(sourceId);
/* 146 */     results.putAll(newResult);
/* 147 */     return new ModelAndView("login", results);
/*     */   }
/*     */   @RequestMapping(method={org.springframework.web.bind.annotation.RequestMethod.POST}, value={"/approveShare.sab"})
/*     */   public ModelAndView approveRequestShare(HttpServletRequest request) {
/* 152 */     return this.userService.approveShare(request);
/*     */   }
/*     */   @RequestMapping(method={org.springframework.web.bind.annotation.RequestMethod.POST}, value={"/requestdoc.sab"})
/*     */   public ModelAndView requestForDocument(HttpServletRequest request) {
/* 157 */     return this.userService.requestForDocument(request);
/*     */   }
/*     */ }

/* Location:           C:\vivek\SABnewnew\ImportedClasses\
 * Qualified Name:     net.sharedauthbased.controller.SharedAuthBasedController
 * JD-Core Version:    0.6.0
 */