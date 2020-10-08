/**
 * @author changer
 * @date 2020/9/27 - 22:40
 */
public class Status {
    int l_missionary;//起始岸传教士
    int l_Savage;//起始岸野人
    int boat;//1:起始岸  0：目的
    int dep;
    //function Assignment
    public Status(int l_missionary_num,int l_Savage_num,int boats){
        this.l_missionary=l_missionary_num;
        this.l_Savage=l_Savage_num;
        this.boat=boats;
    }

    //function Output current status
    public void print_Status(){
        System.out.print("("+this.l_missionary+","+this.l_Savage+","+this.boat+")");
    }

    boolean check_success(){
        if(this.l_Savage==0&& this.boat==0&&this.l_missionary==0){
            return true;
        }
        return false;
    }

}
