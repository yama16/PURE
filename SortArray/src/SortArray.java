import java.util.Comparator;

public class SortArray {
    public static void main(String[] args){

        Student students[] = new Student[5];  //学生インスタンス5つ分の配列

        //配列studentsに5つのStudentインスタンスを登録
        students[0] = new Student("1004", "盛岡四郎", 15);
        students[1] = new Student("1002", "岩手二郎", 17);
        students[2] = new Student("1005", "熊本五郎", 16);
        students[3] = new Student("1001", "青森一郎", 18);
        students[4] = new Student("1003", "福島三郎", 16);

        //並べ替えの前後を決める方法を指定するStudentsComparatorのインスタンスを指定してソ―ト
        MyArrays<Student> sortS = new MyArrays<Student>();
        sortS.sort(students, new StudentsComparator());

        for( Student s : students){ //配列に入っているStudentを一つずつsに取り出しつつループ
            System.out.format("ID:%s\tNAME:%s\tAGE:%2d\n",s.id, s.name, s.age);  //sの情報を出力
        }

        System.out.println("");

        sortS.sort(students, new StudentsComparator2());

        for( Student s : students){ //配列に入っているStudentを一つずつsに取り出しつつループ
            System.out.format("ID:%s\tNAME:%s\tAGE:%2d\n",s.id, s.name, s.age);  //sの情報を出力
        }
    }

}

//
//課題：クラスStudentsComparatorをここに作成
class StudentsComparator implements Comparator<Student>{

	@Override
	public int compare(Student s1,Student s2) {

		return s1.id.compareTo(s2.id);
	}

}

//課題：クラスStudentsComparator2
class StudentsComparator2 implements Comparator<Student>{

	@Override
	public int compare(Student s1, Student s2) {

		int num = Integer.compare(s1.age, s2.age);

		//0の場合昇順に整列
		if(num ==0) {
			return s1.id.compareTo(s2.id);
		}else if(num < 0){
			return 1;
		}else{
			return -1;
		}
	}

}

//課題：クラスMyArray
class MyArrays<T>{

	void sort(T[] t, Comparator<T> sc) {

		quickSort(t,0,(t.length-1),sc);

	}

	void quickSort(T[] t,int left,int right, Comparator<T> sc) {
		MyArrays<T> te = new MyArrays<T>();
		int pivot = ((left+right)/2);
		int l = left;
		int r = right;
		T tmp;

		if (left>=right) {
            return;
        }

        while(l<=r) {
        	//pivotより大きい値を探す
            while(sc.compare(t[l], t[pivot ]) < 0) {
            	l++;
            }
            //pivotより小さい値を探す
            while(sc.compare(t[r], t[pivot]) > 0) {
            	r--;

            }

            if (l<=r) {
                tmp = t[l];
                t[l] = t[r];
                t[r] = tmp;
                l++;
                r--;
            }
        }
        te.quickSort(t,left,r,sc);
        te.quickSort(t, l, right,sc);
	}
}
//


class Student{
    //プログラムをシンプルにするため、カプセル化しない
    public String id;       //学籍番号
    public String name;     //氏名
    public int age;         //年齢

    //コンストラクタ
    //引数  id 学籍番号   name 氏名   age 学年
    public Student( String id, String name, int age){
        this.id = id;
        this.name = name;
        this.age = age;
    }

}

class test{

}
