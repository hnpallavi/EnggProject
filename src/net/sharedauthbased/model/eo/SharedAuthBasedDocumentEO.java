/*     */ package net.sharedauthbased.model.eo;
/*     */ 
/*     */ import java.math.BigInteger;
/*     */ import java.sql.Blob;
/*     */ import java.util.Date;
/*     */ import javax.persistence.Column;
/*     */ import javax.persistence.Entity;
/*     */ import javax.persistence.GeneratedValue;
/*     */ import javax.persistence.GenerationType;
/*     */ import javax.persistence.Id;
/*     */ import javax.persistence.Lob;
/*     */ import javax.persistence.Table;
/*     */ 
/*     */ @Entity
/*     */ @Table(name="SHARED_AUTH_BASED_DOCS")
/*     */ public class SharedAuthBasedDocumentEO
/*     */ {
/*     */ 
/*     */   @Id
/*     */   @GeneratedValue(strategy=GenerationType.AUTO)
/*     */   private Long documentId;
/*     */ 
/*     */   @Column(name="DOCUMENT_NAME")
/*     */   private String documentName;
/*     */ 
/*     */   @Column(name="DOCUMENT_TYPE")
/*     */   private String documentType;
/*     */ 
/*     */   @Column(name="DOCUMENT_DATE")
/*     */   private Date documentDate;
/*     */ 
/*     */   @Column(name="DOCUMENT_CONTENTS")
/*     */   @Lob
/*     */   private Blob documentContents;
/*     */ 
/*     */   @Column(name="DOCUMENT_OWNER")
/*     */   private String documentOwner;
/*     */ 
/*     */   @Column(name="DOCUMENT_KEY")
/*     */   private String documentKey;
/*     */ 
/*     */   @Column(name="RANDOM_KEY")
/*     */   private BigInteger key;
/*     */ 
/*     */   @Column(name="RANDOM_N")
/*     */   private BigInteger N;
/*     */ 
/*     */   public BigInteger getN()
/*     */   {
/*  50 */     return this.N;
/*     */   }
/*     */ 
/*     */   public void setN(BigInteger n) {
/*  54 */     this.N = n;
/*     */   }
/*     */ 
/*     */   public Long getDocumentId() {
/*  58 */     return this.documentId;
/*     */   }
/*     */ 
/*     */   public void setDocumentId(Long documentId) {
/*  62 */     this.documentId = documentId;
/*     */   }
/*     */ 
/*     */   public String getDocumentName() {
/*  66 */     return this.documentName;
/*     */   }
/*     */ 
/*     */   public void setDocumentName(String documentName) {
/*  70 */     this.documentName = documentName;
/*     */   }
/*     */ 
/*     */   public String getDocumentType() {
/*  74 */     return this.documentType;
/*     */   }
/*     */ 
/*     */   public void setDocumentType(String documentType) {
/*  78 */     this.documentType = documentType;
/*     */   }
/*     */ 
/*     */   public Date getDocumentDate() {
/*  82 */     return this.documentDate;
/*     */   }
/*     */ 
/*     */   public void setDocumentDate(Date documentDate) {
/*  86 */     this.documentDate = documentDate;
/*     */   }
/*     */ 
/*     */   public Blob getDocumentContents() {
/*  90 */     return this.documentContents;
/*     */   }
/*     */ 
/*     */   public void setDocumentContents(Blob documentContents) {
/*  94 */     this.documentContents = documentContents;
/*     */   }
/*     */ 
/*     */   public String getDocumentOwner() {
/*  98 */     return this.documentOwner;
/*     */   }
/*     */ 
/*     */   public void setDocumentOwner(String documentOwner) {
/* 102 */     this.documentOwner = documentOwner;
/*     */   }
/*     */ 
/*     */   public String getDocumentKey() {
/* 106 */     return this.documentKey;
/*     */   }
/*     */ 
/*     */   public void setDocumentKey(String documentKey) {
/* 110 */     this.documentKey = documentKey;
/*     */   }
/*     */ 
/*     */   public BigInteger getKey() {
/* 114 */     return this.key;
/*     */   }
/*     */ 
/*     */   public void setKey(BigInteger key) {
/* 118 */     this.key = key;
/*     */   }
/*     */ }

/* Location:           C:\vivek\SABnewnew\ImportedClasses\
 * Qualified Name:     net.sharedauthbased.model.eo.SharedAuthBasedDocumentEO
 * JD-Core Version:    0.6.0
 */