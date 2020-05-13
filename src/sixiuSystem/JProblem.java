package sixiuSystem;

//�ж�����

public class JProblem extends Problem {
	boolean rightans; //��ȷ��
	boolean getans; //�����
	public JProblem() {
		super();
		rightans = getans = false;
		type = 0;
	}
	public JProblem(String text, int points, boolean rightans, String comment) {//����
		super(text, points, comment, 0);
		this.rightans = rightans;
	}
	public String toString() { //ת����ĿΪString
		return super.toString() + "\t(  )\n";
	}
	public int get_rightans() {
		return rightans ? 1 : 0;
	}
	public int get_getans() throws Exception{
		if (!ans_status) throw new Unanswering();
		return getans ? 1 : 0;
	}
	
	public void set_rightans(int rightans) throws Exception{
		if (rightans > 2) throw new OptionCrossBoundary();
		this.rightans = (rightans == 1);
	}
	
	
	public void answering (int getans) throws Exception {//������Ŀ,getansΪ1��ѡ��, Ϊ0��ѡ��
		if (getans >= 2) throw new OptionCrossBoundary();
		this.getans = (getans == 1);
		ans_status = true;
		if (this.getans ^ this.rightans)
			getpoints = 0;
		else getpoints = points;
	}
	public void unanswering () { //����������Ŀ
		ans_status = false;
		getpoints = 0;
	}
}