package sixiuSystem;

//�쳣������Խ�磬��Ҫ���ʵ������Ų�����
public class ProblemCrossBoundary extends Exception{
	static final String msg = "problem cross boundary";
	public ProblemCrossBoundary() {
		super(msg);
	}
}
