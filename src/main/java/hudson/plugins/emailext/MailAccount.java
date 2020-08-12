package hudson.plugins.emailext;

import hudson.Extension;
import hudson.model.AbstractDescribableImpl;
import hudson.model.Descriptor;
import hudson.util.Secret;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.kohsuke.stapler.DataBoundConstructor;
import org.kohsuke.stapler.DataBoundSetter;

import static hudson.Util.nullify;

public class MailAccount extends AbstractDescribableImpl<MailAccount>{
    private String address;
    private String smtpHost;
    private String smtpPort = "25";
    private String smtpUsername;
    private Secret smtpPassword;
    private boolean useSsl;
    private String advProperties;
    private boolean defaultAccount;

    @Deprecated
    public MailAccount(JSONObject jo){
        address = nullify(jo.optString("address", null));
        smtpHost = nullify(jo.optString("smtpHost", null));
        smtpPort = nullify(jo.optString("smtpPort", null));
        if(jo.optBoolean("auth", false)){
            smtpUsername = nullify(jo.optString("smtpUsername", null));
            String pass = nullify(jo.optString("smtpPassword", null));
            if(pass != null) {
                smtpPassword = Secret.fromString(pass);
            }
        }
        useSsl = jo.optBoolean("useSsl", false);
        advProperties = nullify(jo.optString("advProperties", null));
    }

    @DataBoundConstructor
    public MailAccount() {

    }

    public boolean isValid() {
        return StringUtils.isNotBlank(address) && StringUtils.isNotBlank(smtpHost) && (!isAuth() || (StringUtils.isNotBlank(smtpUsername) && smtpPassword != null));
    }

    public boolean isDefaultAccount() {
        return defaultAccount;
    }

    void setDefaultAccount(boolean defaultAccount) {
        this.defaultAccount = defaultAccount;
    }

    @Extension
    public static class MailAccountDescriptor extends Descriptor<MailAccount>{
        @Override
        public String getDisplayName(){
            return "";
        }
    }

    public boolean isAuth(){
        return smtpUsername != null;
    }

    public String getAddress(){
        return address;
    }

    @DataBoundSetter
    public void setAddress(String address){
        this.address = address;
    }

    public String getSmtpHost(){
        return smtpHost;
    }

    @DataBoundSetter
    public void setSmtpHost(String smtpHost){
        this.smtpHost = smtpHost;
    }

    public String getSmtpPort(){
        return smtpPort;
    }

    @DataBoundSetter
    public void setSmtpPort(String smtpPort){
        this.smtpPort = smtpPort;
    }

    public String getSmtpUsername(){
        return smtpUsername;
    }

    @DataBoundSetter
    public void setSmtpUsername(String smtpUsername){
        this.smtpUsername = smtpUsername;
    }

    public Secret getSmtpPassword(){
        return smtpPassword;
    }

    @DataBoundSetter
    public void setSmtpPassword(Secret smtpPassword) {
        this.smtpPassword = smtpPassword;
    }

    public void setSmtpPassword(String smtpPassword) {
        this.smtpPassword = Secret.fromString(smtpPassword);
    }

    public boolean isUseSsl(){
        return useSsl;
    }

    @DataBoundSetter
    public void setUseSsl(boolean useSsl){
        this.useSsl = useSsl;
    }

    public String getAdvProperties(){
        return advProperties;
    }

    @DataBoundSetter
    public void setAdvProperties(String advProperties){
        this.advProperties = advProperties;
    }
}
