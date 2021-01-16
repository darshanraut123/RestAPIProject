package Resources;

public class SingleUser {

	private Data data;
	private Support support;
	
	
	
	@Override
	public String toString() {
		return "SingleUser [data=" + data + ", support=" + support + "]";
	}
	public Data getData() {
		return data;
	}
	public void setData(Data data) {
		this.data = data;
	}
	public Support getSupport() {
		return support;
	}
	public void setSupport(Support support) {
		this.support = support;
	}
	
	
}
