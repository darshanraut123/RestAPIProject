package Resources;

public enum Apis {
	   registerUser("/api/register");

    private String apiVal;

    Apis(String apiVal) {
        this.apiVal = apiVal;
    }

    public String getApiVal() {
        return apiVal;
    }
    
}
