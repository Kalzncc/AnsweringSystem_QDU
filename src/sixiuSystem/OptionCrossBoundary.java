package sixiuSystem;
//�쳣 : ѡ��Խ��,����\����ô�,������Ŀ��ѡ������
public class OptionCrossBoundary extends Exception{
	static final String msg = "Option is cross boundary";
	public OptionCrossBoundary() {
		super(msg);
	}
}
