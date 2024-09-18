package trax.aero.pojo;

public class Print{

	private String sn;

    private String ac;

    private String form_No;

    private String wo;

    private String task_card;

    private String pn;
    
    private String form_Line;

    private String path;

    @Override
    public String toString()
    {
        return " [pn_Sn = "+sn+", ac = "+ac+", form_No = "+form_No+", form_Line = "+form_Line+", wo = "+wo+", task_Card = "+task_card+", pn = "+pn+"]";
    }



	public String getSn() {
		return sn;
	}



	public String getAc() {
		return ac;
	}



	public String getForm_No() {
		return form_No;
	}



	public String getWo() {
		return wo;
	}



	public String getTask_card() {
		return task_card;
	}



	public String getPn() {
		return pn;
	}



	public void setSn(String pn_sn) {
		this.sn = pn_sn;
	}



	public void setAc(String ac) {
		this.ac = ac;
	}



	public void setForm_No(String form_No) {
		this.form_No = form_No;
	}



	public void setWo(String wo) {
		this.wo = wo;
	}



	public void setTask_card(String task_card) {
		this.task_card = task_card;
	}



	public void setPn(String pn) {
		this.pn = pn;
	}



	public String getForm_Line() {
		return form_Line;
	}



	public void setForm_Line(String form_Line) {
		this.form_Line = form_Line;
	}



	public String getPath() {
		return path;
	}



	public void setPath(String path) {
		this.path = path;
	}
	
	
}
