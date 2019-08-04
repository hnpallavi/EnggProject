/*     */ package net.sharedauthbased.model.eo;
/*     */ 
/*     */ import java.math.BigInteger;
/*     */ import java.util.Date;
/*     */ import javax.persistence.Column;
/*     */ import javax.persistence.Entity;
/*     */ import javax.persistence.Id;
/*     */ import javax.persistence.Table;
/*     */ import javax.persistence.Transient;
/*     */ 
/*     */ @Entity
/*     */ @Table(name="SHARED_AUTH_BASED_USER_T")
/*     */ public class SharedAuthBasedUserEO
/*     */ {
/*     */ 
/*     */   @Transient
/*     */   private BigInteger usersPublicKey;
/*     */ 
/*     */   @Id
/*     */   @Column(name="SHARED_AUTH_USERID")
/*     */   private String userId;
/*     */ 
/*     */   @Column(name="SHARED_AUTH_USERNAME")
/*     */   private String userName;
/*     */ 
/*     */   @Column(name="SHARED_AUTH_USEREMAIL")
/*     */   private String userEmail;
/*     */ 
/*     */   @Column(name="SHARED_AUTH_USERPH")
/*     */   private String userPhone;
/*     */ 
/*     */   @Column(name="SHARED_AUTH_USERPASS")
/*     */   private String userPassword;
/*     */ 
/*     */   @Column(name="SHARED_AUTH_ISFIRST_LOGIN")
/*     */   private Boolean userIsFirstLogin;
/*     */ 
/*     */   @Column(name="SHARED_AUTH_CREATED_DATE")
/*     */   private Date userCreatedDate;
/*     */ 
/*     */   @Column(name="SHARED_AUTH_PK")
/*     */   private BigInteger PK;
/*     */ 
/*     */   @Column(name="SHARED_AUTH_N")
/*     */   private BigInteger N;
/*     */ 
/*     */   @Column(name="SHARED_AUTH_SK")
/*     */   private BigInteger SK;
/*     */ 
/*     */   @Column(name="SHARED_AUTH_PhiN")
/*     */   private BigInteger PhiN;
/*     */ 
/*     */   @Column(name="SHARED_AUTH_RK")
/*     */   private BigInteger RK;
/*     */ 
/*     */   @Column(name="SHARED_AUTH_Cipher")
/*     */   private BigInteger Cipher;
/*     */ 
/*     */   public BigInteger getCipher()
/*     */   {
/*  94 */     return this.Cipher;
/*     */   }
/*     */ 
/*     */   public void setCipher(BigInteger cipher) {
/*  98 */     this.Cipher = cipher;
/*     */   }
/*     */ 
/*     */   public BigInteger getUsersPublicKey()
/*     */   {
/* 105 */     return this.usersPublicKey;
/*     */   }
/*     */ 
/*     */   public void setUsersPublicKey(BigInteger usersPublicKey) {
/* 109 */     this.usersPublicKey = usersPublicKey;
/*     */   }
/*     */ 
/*     */   public String getUserId() {
/* 113 */     return this.userId;
/*     */   }
/*     */ 
/*     */   public void setUserId(String userId) {
/* 117 */     this.userId = userId;
/*     */   }
/*     */ 
/*     */   public String getUserName() {
/* 121 */     return this.userName;
/*     */   }
/*     */ 
/*     */   public void setUserName(String userName) {
/* 125 */     this.userName = userName;
/*     */   }
/*     */ 
/*     */   public String getUserEmail() {
/* 129 */     return this.userEmail;
/*     */   }
/*     */ 
/*     */   public void setUserEmail(String userEmail) {
/* 133 */     this.userEmail = userEmail;
/*     */   }
/*     */ 
/*     */   public String getUserPhone() {
/* 137 */     return this.userPhone;
/*     */   }
/*     */ 
/*     */   public void setUserPhone(String userPhone) {
/* 141 */     this.userPhone = userPhone;
/*     */   }
/*     */ 
/*     */   public String getUserPassword() {
/* 145 */     return this.userPassword;
/*     */   }
/*     */ 
/*     */   public void setUserPassword(String userPassword) {
/* 149 */     this.userPassword = userPassword;
/*     */   }
/*     */ 
/*     */   public BigInteger getRK() {
/* 153 */     return this.RK;
/*     */   }
/*     */ 
/*     */   public void setRK(BigInteger RK) {
/* 157 */     this.RK = RK;
/*     */   }
/*     */ 
/*     */   public Boolean getUserIsFirstLogin() {
/* 161 */     return this.userIsFirstLogin;
/*     */   }
/*     */ 
/*     */   public void setUserIsFirstLogin(Boolean userIsFirstLogin) {
/* 165 */     this.userIsFirstLogin = userIsFirstLogin;
/*     */   }
/*     */ 
/*     */   public Date getUserCreatedDate() {
/* 169 */     return this.userCreatedDate;
/*     */   }
/*     */ 
/*     */   public void setUserCreatedDate(Date userCreatedDate) {
/* 173 */     this.userCreatedDate = userCreatedDate;
/*     */   }
/*     */ 
/*     */   public BigInteger getPK() {
/* 177 */     return this.PK;
/*     */   }
/*     */ 
/*     */   public void setPK(BigInteger PK) {
/* 181 */     this.PK = PK;
/*     */   }
/*     */ 
/*     */   public BigInteger getSK() {
/* 185 */     return this.SK;
/*     */   }
/*     */ 
/*     */   public void setSK(BigInteger SK) {
/* 189 */     this.SK = SK;
/*     */   }
/*     */ 
/*     */   public BigInteger getPhiN() {
/* 193 */     return this.PhiN;
/*     */   }
/*     */ 
/*     */   public void setPhiN(BigInteger PhiN) {
/* 197 */     this.PhiN = PhiN;
/*     */   }
/*     */ 
/*     */   public BigInteger getN() {
/* 201 */     return this.N;
/*     */   }
/*     */ 
/*     */   public void setN(BigInteger N) {
/* 205 */     this.N = N;
/*     */   }
/*     */ }

/* Location:           C:\vivek\SABnewnew\ImportedClasses\
 * Qualified Name:     net.sharedauthbased.model.eo.SharedAuthBasedUserEO
 * JD-Core Version:    0.6.0
 */