/*    */ package net.sharedauthbased.service.impl;
/*    */ 
/*    */ import java.util.Map;
/*    */ import javax.servlet.http.HttpServletRequest;
/*    */ import net.sharedauthbased.dao.SharedAuthBasedUserDAO;
/*    */ import net.sharedauthbased.model.eo.SharedAuthBasedDocumentEO;
/*    */ import net.sharedauthbased.service.SharedAuthBasedUserService;
/*    */ import org.springframework.beans.factory.annotation.Autowired;
/*    */ import org.springframework.stereotype.Service;
/*    */ import org.springframework.transaction.annotation.Propagation;
/*    */ import org.springframework.transaction.annotation.Transactional;
/*    */ import org.springframework.web.servlet.ModelAndView;
/*    */ 
/*    */ @Service
/*    */ @Transactional(propagation=Propagation.SUPPORTS, readOnly=false)
/*    */ public class SharedAuthBasedUserServiceImpl
/*    */   implements SharedAuthBasedUserService
/*    */ {
/*    */ 
/*    */   @Autowired
/*    */   private SharedAuthBasedUserDAO userDAO;
/*    */ 
/*    */   public ModelAndView createUser(HttpServletRequest request)
/*    */   {
/* 26 */     return this.userDAO.createUser(request);
/*    */   }
/*    */ 
/*    */   public ModelAndView doLogin(HttpServletRequest request)
/*    */   {
/* 31 */     return this.userDAO.doLogin(request);
/*    */   }
/*    */ 
/*    */   public ModelAndView forgotPassword(HttpServletRequest request)
/*    */   {
/* 36 */     return this.userDAO.forgotPassword(request);
/*    */   }
/*    */ 
/*    */   public ModelAndView updatePassword(HttpServletRequest request)
/*    */   {
/* 41 */     return this.userDAO.updatePassword(request);
/*    */   }
/*    */ 
/*    */   public ModelAndView uploadFile(SharedAuthBasedDocumentEO doc, HttpServletRequest request)
/*    */   {
/* 47 */     return this.userDAO.uploadFile(doc, request);
/*    */   }
/*    */ 
/*    */   public SharedAuthBasedDocumentEO getFileToDownload(String documentId, String privateKey)
/*    */   {
/* 53 */     return this.userDAO.getFileToDownload(documentId, privateKey);
/*    */   }
/*    */ 
/*    */   public Map getResults(String userId)
/*    */   {
/* 58 */     return this.userDAO.getResults(userId);
/*    */   }
/*    */ 
/*    */   public Object shareRequest(String sourceId, String destId)
/*    */   {
/* 63 */     return this.userDAO.shareRequest(sourceId, destId);
/*    */   }
/*    */ 
/*    */   public ModelAndView approveShare(HttpServletRequest request)
/*    */   {
/* 68 */     return this.userDAO.approveShare(request);
/*    */   }
/*    */ 
/*    */   public ModelAndView requestForDocument(HttpServletRequest request)
/*    */   {
/* 73 */     return this.userDAO.requestForDocument(request);
/*    */   }
/*    */ }

/* Location:           C:\vivek\SABnewnew\ImportedClasses\
 * Qualified Name:     net.sharedauthbased.service.impl.SharedAuthBasedUserServiceImpl
 * JD-Core Version:    0.6.0
 */