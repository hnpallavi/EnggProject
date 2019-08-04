/*    */ package net.sharedauthbased.utilities;
/*    */ 
/*    */ import java.util.List;
/*    */ import org.apache.commons.lang.RandomStringUtils;
/*    */ import org.hibernate.Query;
/*    */ import org.hibernate.Session;
/*    */ 
/*    */ public class UserPropertyGenerator
/*    */ {
/*    */   private static final String userIdPrefix = "SharedAuthBasedUser";
/*    */ 
/*    */   public static String getNewUserId(Session session)
/*    */   {
/* 14 */     Query query = session.createQuery("from SharedAuthBasedUserEO");
/* 15 */     List list = query.list();
/* 16 */     int count = list.size();
/* 17 */     count++;
/* 18 */     return "SharedAuthBasedUser" + String.valueOf(count);
/*    */   }
/*    */ 
/*    */   public static String getNewPassword() {
/* 22 */     return RandomStringUtils.randomAlphanumeric(8);
/*    */   }
/*    */ }

/* Location:           C:\vivek\SABnewnew\ImportedClasses\
 * Qualified Name:     net.sharedauthbased.utilities.UserPropertyGenerator
 * JD-Core Version:    0.6.0
 */