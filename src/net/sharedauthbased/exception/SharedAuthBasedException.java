/*     */ package net.sharedauthbased.exception;
/*     */ 
/*     */ import java.io.PrintStream;
/*     */ import java.io.PrintWriter;
/*     */ 
/*     */ public class SharedAuthBasedException extends Exception
/*     */ {
/*     */   public String exception;
/*     */ 
/*     */   public SharedAuthBasedException(String exception)
/*     */   {
/*  11 */     this.exception = exception;
/*     */   }
/*     */ 
/*     */   public synchronized Throwable fillInStackTrace()
/*     */   {
/*  17 */     return super.fillInStackTrace();
/*     */   }
/*     */ 
/*     */   public synchronized Throwable getCause()
/*     */   {
/*  23 */     return super.getCause();
/*     */   }
/*     */ 
/*     */   public String getLocalizedMessage()
/*     */   {
/*  29 */     return super.getLocalizedMessage();
/*     */   }
/*     */ 
/*     */   public String getMessage()
/*     */   {
/*  34 */     return "Application error occured , please contact support with following error stack ------->" + this.exception;
/*     */   }
/*     */ 
/*     */   public StackTraceElement[] getStackTrace()
/*     */   {
/*  40 */     return super.getStackTrace();
/*     */   }
/*     */ 
/*     */   public synchronized Throwable initCause(Throwable arg0)
/*     */   {
/*  46 */     return super.initCause(arg0);
/*     */   }
/*     */ 
/*     */   public void printStackTrace()
/*     */   {
/*  52 */     super.printStackTrace();
/*     */   }
/*     */ 
/*     */   public void printStackTrace(PrintStream arg0)
/*     */   {
/*  58 */     super.printStackTrace(arg0);
/*     */   }
/*     */ 
/*     */   public void printStackTrace(PrintWriter arg0)
/*     */   {
/*  64 */     super.printStackTrace(arg0);
/*     */   }
/*     */ 
/*     */   public void setStackTrace(StackTraceElement[] arg0)
/*     */   {
/*  70 */     super.setStackTrace(arg0);
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/*  76 */     return super.toString();
/*     */   }
/*     */ 
/*     */   protected Object clone()
/*     */     throws CloneNotSupportedException
/*     */   {
/*  82 */     return super.clone();
/*     */   }
/*     */ 
/*     */   public boolean equals(Object obj)
/*     */   {
/*  88 */     return super.equals(obj);
/*     */   }
/*     */ 
/*     */   protected void finalize()
/*     */     throws Throwable
/*     */   {
/*  94 */     super.finalize();
/*     */   }
/*     */ 
/*     */   public int hashCode()
/*     */   {
/* 100 */     return super.hashCode();
/*     */   }
/*     */ }

/* Location:           C:\vivek\SABnewnew\ImportedClasses\
 * Qualified Name:     net.sharedauthbased.exception.SharedAuthBasedException
 * JD-Core Version:    0.6.0
 */