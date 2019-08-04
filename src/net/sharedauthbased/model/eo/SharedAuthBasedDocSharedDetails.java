/*    */ package net.sharedauthbased.model.eo;
/*    */ 
/*    */ import javax.persistence.Column;
/*    */ import javax.persistence.Entity;
/*    */ import javax.persistence.GeneratedValue;
/*    */ import javax.persistence.GenerationType;
/*    */ import javax.persistence.Id;
/*    */ import javax.persistence.Table;
/*    */ 
/*    */ @Entity
/*    */ @Table(name="SHARED_AUTH_BASED_SHARING_DETAILSs")
/*    */ public class SharedAuthBasedDocSharedDetails
/*    */ {
/*    */ 
/*    */   @Id
/*    */   @GeneratedValue(strategy=GenerationType.AUTO)
/*    */   private Long shareId;
/*    */ 
/*    */   @Column(name="SOURCE")
/*    */   private String sourceOwner;
/*    */ 
/*    */   @Column(name="DESTINATION")
/*    */   private String destinationOwner;
/*    */ 
/*    */   @Column(name="STATUS")
/*    */   private String status;
/*    */ 
/*    */   public Long getShareId()
/*    */   {
/* 28 */     return this.shareId;
/*    */   }
/*    */ 
/*    */   public void setShareId(Long shareId) {
/* 32 */     this.shareId = shareId;
/*    */   }
/*    */ 
/*    */   public String getSourceOwner() {
/* 36 */     return this.sourceOwner;
/*    */   }
/*    */ 
/*    */   public void setSourceOwner(String sourceOwner) {
/* 40 */     this.sourceOwner = sourceOwner;
/*    */   }
/*    */ 
/*    */   public String getDestinationOwner() {
/* 44 */     return this.destinationOwner;
/*    */   }
/*    */ 
/*    */   public void setDestinationOwner(String destinationOwner) {
/* 48 */     this.destinationOwner = destinationOwner;
/*    */   }
/*    */ 
/*    */   public String getStatus() {
/* 52 */     return this.status;
/*    */   }
/*    */ 
/*    */   public void setStatus(String status) {
/* 56 */     this.status = status;
/*    */   }
/*    */ }

/* Location:           C:\vivek\SABnewnew\ImportedClasses\
 * Qualified Name:     net.sharedauthbased.model.eo.SharedAuthBasedDocSharedDetails
 * JD-Core Version:    0.6.0
 */