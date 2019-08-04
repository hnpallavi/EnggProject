package net.sharedauthbased.service;

import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import net.sharedauthbased.model.eo.SharedAuthBasedDocumentEO;
import org.springframework.web.servlet.ModelAndView;

public abstract interface SharedAuthBasedUserService
{
  public abstract ModelAndView createUser(HttpServletRequest paramHttpServletRequest);

  public abstract ModelAndView doLogin(HttpServletRequest paramHttpServletRequest);

  public abstract ModelAndView forgotPassword(HttpServletRequest paramHttpServletRequest);

  public abstract ModelAndView updatePassword(HttpServletRequest paramHttpServletRequest);

  public abstract ModelAndView uploadFile(SharedAuthBasedDocumentEO paramSharedAuthBasedDocumentEO, HttpServletRequest paramHttpServletRequest);

  public abstract SharedAuthBasedDocumentEO getFileToDownload(String paramString1, String paramString2);

  public abstract Map getResults(String paramString);

  public abstract Object shareRequest(String paramString1, String paramString2);

  public abstract ModelAndView approveShare(HttpServletRequest paramHttpServletRequest);

  public abstract ModelAndView requestForDocument(HttpServletRequest paramHttpServletRequest);
}

/* Location:           C:\vivek\SABnewnew\ImportedClasses\
 * Qualified Name:     net.sharedauthbased.service.SharedAuthBasedUserService
 * JD-Core Version:    0.6.0
 */