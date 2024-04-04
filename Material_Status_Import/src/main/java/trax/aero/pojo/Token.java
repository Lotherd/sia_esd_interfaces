package trax.aero.pojo;

public class Token {

	private String access_token;

    private String resource;

    private String not_before;

    private String expires_on;

    private String token_type;

    private String expires_in;

    public String getAccess_token ()
    {
        return access_token;
    }

    public void setAccess_token (String access_token)
    {
        this.access_token = access_token;
    }

    public String getResource ()
    {
        return resource;
    }

    public void setResource (String resource)
    {
        this.resource = resource;
    }

    public String getNot_before ()
    {
        return not_before;
    }

    public void setNot_before (String not_before)
    {
        this.not_before = not_before;
    }

    public String getExpires_on ()
    {
        return expires_on;
    }

    public void setExpires_on (String expires_on)
    {
        this.expires_on = expires_on;
    }

    public String getToken_type ()
    {
        return token_type;
    }

    public void setToken_type (String token_type)
    {
        this.token_type = token_type;
    }

    public String getExpires_in ()
    {
        return expires_in;
    }

    public void setExpires_in (String expires_in)
    {
        this.expires_in = expires_in;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [access_token = "+access_token+", resource = "+resource+", not_before = "+not_before+", expires_on = "+expires_on+", token_type = "+token_type+", expires_in = "+expires_in+"]";
    }
	
}
