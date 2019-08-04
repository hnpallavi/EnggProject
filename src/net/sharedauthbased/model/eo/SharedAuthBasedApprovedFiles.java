/*    */ package net.sharedauthbased.model.eo;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ import javax.persistence.Column;
/*    */ import javax.persistence.Entity;
/*    */ import javax.persistence.FetchType;
/*    */ import javax.persistence.GeneratedValue;
/*    */ import javax.persistence.GenerationType;
/*    */ import javax.persistence.Id;
/*    */ import javax.persistence.Table;
/*    */ import org.hibernate.annotations.CollectionOfElements;
/*    */ 
/*    */ @Entity
/*    */ @Table(name="SHARED_AUTH_BASED_APP_FILES")
/*    */ public class SharedAuthBasedApprovedFiles
/*    */ {
/*    */ 
/*    */   @Id
/*    */   @GeneratedValue(strategy=GenerationType.AUTO)
/*    */   private Long sharedId;
/*    */ 
/*    */   @Column(name="owner")
/*    */   private String sharedOwner;
/*    */ 
/*    */   @CollectionOfElements(fetch=FetchType.EAGER)
/* 28 */   private List<Long> listOfDocuments = new ArrayList();
/*    */ 
/*    */   public Long getSharedId() {
/* 31 */     return this.sharedId;
/*    */   }
/*    */ 
/*    */   public void setSharedId(Long sharedId) {
/* 35 */     this.sharedId = sharedId;
/*    */   }
/*    */ 
/*    */   public String getSharedOwner() {
/* 39 */     return this.sharedOwner;
/*    */   }
/*    */ 
/*    */   public void setSharedOwner(String sharedOwner) {
/* 43 */     this.sharedOwner = sharedOwner;
/*    */   }
/*    */ 
/*    */   public List<Long> getListOfDocuments() {
/* 47 */     return this.listOfDocuments;
/*    */   }
/*    */ 
/*    */   public void setListOfDocuments(List<Long> listOfDocuments) {
/* 51 */     this.listOfDocuments = listOfDocuments;
/*    */   }
/*    */ }

/* Location:           C:\vivek\SABnewnew\ImportedClasses\
 * Qualified Name:     net.sharedauthbased.model.eo.SharedAuthBasedApprovedFiles
 * JD-Core Version:    0.6.0
 */