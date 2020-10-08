import java.util.*;

/**
 * @author changer
 * @date 2020/9/27 - 17:143
 */
public class Main {
    static int N;// n:missionary   n:savage
    static int K;// 船最高容纳
    static ArrayList<Status> State_1= new ArrayList<Status>();//状态记录
    static ArrayList<Integer> dep_new=new  ArrayList<Integer>();//搜索深度记录表，用于比较最短路径
    static ArrayList<ArrayList<Status>> State_3=new ArrayList<ArrayList<Status>>();
    public static boolean check_status(ArrayList<Status> s,Status c) {
        //判断合法状态
        //1.表中不可以重复
        if (!s.isEmpty()) {
            for (int i = 0; i < s.size(); ++i) {
                if (s.get(i).boat == c.boat && s.get(i).l_Savage == c.l_Savage && s.get(i).l_missionary == c.l_missionary) {
                    return false;
                }
            }
        }
        //2.是否满足 missionary>=savage
        if(c.l_missionary!=0&&c.l_missionary<c.l_Savage){
            return false;
        }

        if((N-c.l_missionary!=0) && (N-c.l_missionary<N-c.l_Savage)){
            return false;
        }
        return true;
    }

    public static void bfs(Status s){
        int d=0;
        if(s.check_success()){//如果成功，输出结果；
            ArrayList<Status> State_2= new ArrayList<Status>();
            System.out.println();
            for(int i=0;i<State_1.size();++i){
                 State_1.get(i).print_Status();
                 State_2.add(State_1.get(i));
                 if(i<State_1.size()-1){
                     System.out.print("-->");
                     d++;
                 }
            }
            dep_new.add(d);
            //State_2.add(State_1.get(State_1.size()-1).dep);//将数组填充到State_2中
            State_3.add(State_2);//将当前状态存入State_3中
            return;
        }

        //判断船上最多的野人、传教士数量
        int boat_max_missionary;
        int boat_max_savage;
        if(s.boat==1){
            boat_max_missionary= Math.min(s.l_missionary, K);
            boat_max_savage= Math.min(s.l_Savage, K);
        }else{
            boat_max_missionary=Math.min(N-s.l_missionary,K);
            boat_max_savage=Math.min(N-s.l_Savage,K);
        }

        //开始列举过河的所有情况
        for(int i=0;i<=boat_max_missionary;++i){
            int m1=i;//船上传教士的数量
            for(int j = 0; j<=(Math.min((K - m1), boat_max_savage)); ++j){
                int s1=j;//船上野人的数量
                int new_left_missionary,new_left_savage,new_boat;
                if(m1==0&&s1==0&&s.boat==0){
                    continue;
                }
                Status new_s;
                if(s.boat==1){//船从左岸可以开往右岸
                    new_boat=0;
                    new_left_missionary=s.l_missionary-m1;//传教士数量=原先岸上传教士的数量减去上船的传教士人数
                    new_left_savage=s.l_Savage-s1;//同上↑
                    new_s= new Status(new_left_missionary,new_left_savage,new_boat);
                    if(check_status(State_1,new_s)){
                        //d++;
                        //new_s.dep=d;
                        State_1.add(new_s);//检查完毕，没错、入表
                        bfs(new_s);
                        State_1.remove(new_s);
                    }
                }else{//从右岸到左岸
                    new_boat=1;
                    new_left_missionary=s.l_missionary+m1;
                    new_left_savage=s.l_Savage+s1;
                    new_s=new Status(new_left_missionary,new_left_savage,new_boat);
                    if(check_status(State_1,new_s)){
                       // d++;
                       // new_s.dep=d;
                        State_1.add(new_s);
                        bfs(new_s);
                        State_1.remove(new_s);
                    }
                }
            }
        }return;
    }

    public static void main(String[] args) {
        Scanner in=new Scanner(System.in);
        System.out.println("请输入野人/传教士的人数：");
        N=in.nextInt();
        System.out.println("请输入船的最大容量：");
        K=in.nextInt();
        Status s=new Status(N,N,1);//1:船在左；0：船在右
        State_1.add(s);
        bfs(s);
        Collections.sort(dep_new);
        System.out.println();
        System.out.println("最短路径为：");
        for(int i=0;i<State_3.size();++i){
            if((State_3.get(i).size()-1)==dep_new.get(0)){
                for(int j=0;j<State_3.get(i).size();++j){
                    State_3.get(i).get(j).print_Status();
                    if(j<State_3.get(i).size()-1){
                        System.out.print("-->");
                    }
                }
                System.out.println();
            }

        }
    }

}
