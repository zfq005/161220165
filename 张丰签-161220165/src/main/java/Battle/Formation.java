package Battle;

public class Formation {
    private int x_size;
    private int y_size;
    BattleField battleField;
    public Formation(int x,int y,BattleField b){
        this.x_size=x;
        this.y_size=y;
        this.battleField=b;
    }
    private void formation1(){
        int bro_pos=0;
        int cur_NO=11;
        int last_x=0,last_y=0;
        for(int i=0;i<7;i++){
            if(i==0){
                battleField.set_one(x_size-2,y_size/2,cur_NO);
                battleField.set_one(0,y_size/2+bro_pos,i+1);
                last_x=1;last_y=1;
                cur_NO++;
                bro_pos++;
            }
            else if(i%2==1){
                battleField.set_one(x_size-last_x-2,y_size/2+last_y,cur_NO);
                battleField.set_one(0,y_size/2-bro_pos,i+1);
                cur_NO++;
            }
            else{
                battleField.set_one(x_size-last_x-2,y_size/2-last_y,cur_NO);
                battleField.set_one(0,y_size/2+bro_pos,i+1);
                last_x++;last_y++;
                cur_NO++;
                bro_pos++;
            }
        }
        battleField.set_one(1,y_size/2,8);
        battleField.set_one(x_size/2,y_size/2,9);
        battleField.set_one(x_size/2-1,y_size/2,10);
      //  battleField.show_all();
    }
    private void formation2(){
        int bro_pos=0;
        int cur_NO=11;
        int last_x=0,last_y=0;
        for(int i=0;i<7;i++){
            if(i==0){
                battleField.set_one(x_size-1,y_size/4,cur_NO);
                battleField.set_one(0,y_size/2+bro_pos,i+1);
                last_x=1;last_y=1;
                cur_NO++;
                bro_pos++;
            }
            else if(i%2==1){
                battleField.set_one(x_size-last_x-1,y_size/4+last_y,cur_NO);
                battleField.set_one(0,y_size/2-bro_pos,i+1);
                cur_NO++;
                last_x++;last_y++;
            }
            else{
                battleField.set_one(x_size-last_x-1,y_size/4+last_y,cur_NO);
                battleField.set_one(0,y_size/2+bro_pos,i+1);
                last_x++;last_y++;
                cur_NO++;
                bro_pos++;
            }
        }
        battleField.set_one(1,y_size/2,8);
        battleField.set_one(x_size/2,y_size/2,9);
        battleField.set_one(x_size/2-1,y_size/2,10);
        //  battleField.show_all();
    }
    private void formation3(){
        int bro_pos=0;
        for(int i=0;i<7;i++){
            if(i==0){
                battleField.set_one(0,y_size/2+bro_pos,i+1);
                bro_pos++;
            }
            else if(i%2==1){
                battleField.set_one(0,y_size/2-bro_pos,i+1);
            }
            else{
                battleField.set_one(0,y_size/2+bro_pos,i+1);
                bro_pos++;
            }
        }
        battleField.set_one(1,y_size/2,8);
        battleField.set_one(x_size/2,y_size/2,9);
        battleField.set_one(x_size/2-1,y_size/2,10);
        battleField.set_one(x_size/2+3,y_size/2,11);
        battleField.set_one(x_size/2+5,y_size/2,12);
        battleField.set_one(x_size/2+7,y_size/2,13);
        battleField.set_one(x_size/2+4,y_size/2+2,14);
        battleField.set_one(x_size/2+4,y_size/2-2,15);
        battleField.set_one(x_size/2+5,y_size/2+3,16);
        battleField.set_one(x_size/2+5,y_size/2-3,17);
       // battleField.show_all();
    }
    private void formation4(){
        int bro_pos=0;
        for(int i=0;i<7;i++){
            if(i==0){
                battleField.set_one(0,y_size/2+bro_pos,i+1);
                bro_pos++;
            }
            else if(i%2==1){
                battleField.set_one(0,y_size/2-bro_pos,i+1);
            }
            else{
                battleField.set_one(0,y_size/2+bro_pos,i+1);
                bro_pos++;
            }
        }
        battleField.set_one(1,y_size/2,8);
        battleField.set_one(x_size/2,y_size/2,9);
        battleField.set_one(x_size/2-1,y_size/2,10);
        battleField.set_one(x_size-1,y_size/2,11);
        battleField.set_one(x_size-1,y_size/2+2,12);
        battleField.set_one(x_size-1,y_size/2-2,13);
        battleField.set_one(x_size-3,y_size/2+2,14);
        battleField.set_one(x_size-5,y_size/2+3,15);
        battleField.set_one(x_size-3,y_size/2-2,16);
        battleField.set_one(x_size-5,y_size/2-3,17);
       // battleField.show_all();
    }
    public void change_formation(int num){
        switch (num){
            case 1:formation1();break;
            case 2:formation2();break;
            case 3:formation3();break;
            case 4:formation4();break;
            default:break;
        }
    }
}
