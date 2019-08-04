/*    */ package net.sharedauthbased.model.eo;
/*    */ 
/*    */ public class SharedAuthBasedSMSData
/*    */ {
/*    */   private String userName;
/*    */   private String userPassword;
/*    */ 
/*    */   public SharedAuthBasedSMSData(String userName, String userPassword)
/*    */   {
/* 10 */     this.userName = userName;
/* 11 */     this.userPassword = userPassword;
/*    */   }
/*    */ 
/*    */   public String getUserName() {
/* 15 */     return this.userName;
/*    */   }
/*    */ 
/*    */   public String getUserPassword() {
/* 19 */     return this.userPassword;
/*    */   }
/*    */ }

/* Location:           C:\vivek\SABnewnew\ImportedClasses\
 * Qualified Name:     net.sharedauthbased.model.eo.SharedAuthBasedSMSData
 * JD-Core Version:    0.6.0
 */