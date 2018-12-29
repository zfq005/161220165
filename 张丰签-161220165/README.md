# 葫芦娃大作业
### 一、文件结构
###### 1. Battle文件夹
   
   ① BattleField.java
   
   ② BattleRecord.java
   
   ③ Formation.java
   
###### 2. Beings文件夹
   
   ① BeingsImage.java
   
   ② Brother.java
   
   ③ Creature.java
   
   ④ Grandpa.java
   
   ⑤ Leader.java
   
   ⑥ Snake.java
   
   ⑦ Soldier.java
   
##### 3. GUI文件夹   
   
   ① BrotherThread.java
   
   ② GrandpaThread.java
   
   ③ LeaderThread.java
   
   ④ RecordThread.java
   
   ⑤ SnakeThread.java
   
   ⑥ SoldierThread.java
   
4. Controller.java   

5. Main.java

### 二、实现思路

###### 生物表示：
   
![Image](https://github.com/zfq005/zfq/raw/master/1.png)

所有子类均继承自Creature，在Creature中实现对外的具体接口

##### 战场表示：

由一个int类型的二维数组表示战场，每个生物有自己独特的ID(Creature类中的int变量number)，用0代表该位置为空，其他特有的ID表示该位置站着哪个生物。

生物用一个Creature数组表示，BattleField中实现了初始化、移动、战斗等对外接口，同时自己内部又去调用Formation的对象，根据外部传来的参数来改变阵型。

##### 图形界面：

用JavaFX实现图形界面，在界面中插入BorderPane，向其中添加ImageView窗口，每个Creature拥有自己的ImageView，向这些窗口中添加每个生物的Image信息
即可显示每个生物的图像。同时利用键盘事件，来判断战斗是否开始、是否读取文件进行战斗回放的功能。

##### 多线程：

根据生物创建线程，每个线程中实现相关功能，因为每个线程都会访问到BattleField类的对象，会访问到临界区的变量，因此要利用synchronized()来实现仅有一个线程会去触碰这些临界区变量。

##### 输入输出：

利用Java提供的输入输出流进行文件的读写操作。


### 三、Battle文件夹内代码介绍

##### BattleField.java

```
//对象及变量：
    private int x_size; 
    private int y_size;
    private int[][] battlefield;
    //表示战场的二维数组以及战场大小信息

    private Creature[] creatures;
    private Brother[] brothers;
    private Soldier[] soldiers;
    private Snake snake;
    private Grandpa grandpa;
    private Leader leader;
    //各种生物对象，主要用creatures进行各种操作

    private boolean isEnd=true;       //战斗是否结束
    private boolean writeReady=false; //是否准备好写记录
    private boolean isRecord=false;   //是否是回放战斗
    //判断战斗的几个标志

    private Formation formation;
    private int formationType=1;
    //阵型信息
    
    private BattleRecord[] records=new BattleRecord[1000];
    private int recordLength=0;
    //记录信息
    
//方法：
    public BattleField();
    //初始化战场以及对生物对象初始化
    
    public Creature getCreature(int num);
    //对外接口，根据num的值获得对应的生物
    
    public Formation getFormation();
    //对外接口，获得阵型的对象
    
    public void setFormation(int num);
    //对外接口，设置阵型，num的取值范围为1-4，修改formationType
    
    public int getFormationType();
    //对外接口，获得formationType的值
    
    public BattleRecord[] getRecord();
    
    public int getRecordLength();
    
    public void setRecordLength(int num);
    //这几个均是对外进行记录信息操作的方法
    
    public boolean getIsEnd();
    
    public boolean getIsRecord();
    
    public void setIsRecord(boolean value);
    
    public void setIsEnd(boolean value);
    //获得或设置战斗标志
    
    public void show_all();
    //打印阵型，主要用于test阵型是否设置好
    
    public boolean set_one(int x,int y,int num);
    //在Formation中调用，设置(x,y)的坐标为生物编号为num的生物，进行了数组越界的异常处理，以及该位置是否有生物的判断
    
    private boolean BrotherMove(int num,int x,int y);
    
    private boolean GrandpaMove(int x,int y);
    
    private boolean SoldierMove(int num,int x,int y);
    //几个生物的移动，以及移动产生的结果：是否移动成功、是否发生战斗
    
    public boolean move(int x,int y,int to_x,int to_y);
    //对外函数，将(x,y)位置上的生物移动到(to_x,to_y)，根据生物类型调用上述生物的移动函数，同时写记录
    
    private int RandomAttack(int num);
    //随机数，根据num随机产生攻击对象
    
    private void BadBattle(int src,int dst);
    
    private void GoodBattle(int src,int dst);
    //好坏阵营的战斗，战斗胜负随机产生，但概率不同
    
    private void set_end();
    //每次战斗结束调用该函数，判断整场战斗是否结束
    
    public void initFormation();
    
    public void setImage();
    //调用初始化生物的图形的函数
    
    public void writerecord();
    //写记录
    
    public void RecordPlay(int i);
    //回放
    

```
关于攻击设定和移动设定：蛇精和蝎子精固定不动，随机对Good阵营的生物进行远距离攻击，有1/3的几率在战斗中死亡；Good阵营的生物每次向前移动一格，若前方有队友，则向下移动；若前方有敌人，且敌人未死亡，则攻击敌人，有1/3的几率在战斗中死亡，当爷爷进入到了战场1/3的位置就可以展开远程攻击，随机对小兵进行输出，若前方有敌人，但敌人已经死亡，则可以进行随机的远程攻击。小兵则是有一定几率向前移动，当面前有敌人就可以对其展开攻击。

##### BattleRecord.java

```

public class BattleRecord {
    public int type;
    public int src_num;
    public int src_x;
    public int src_y;
    public int dst_x;
    public int dst_y;
}
/*
用于记录战斗信息，type为类型，1为移动信息，2为战斗信息；
src_num为发起该动作的生物；
(src_x,src_y)为当前位置；
(dst_x,dst_y)为目标位置；
该记录只记录死亡对象，和死亡地点
*/
```

##### Formation.java

```
    public void change_formation(int num){
        switch (num){
            case 1:formation1();break;
            case 2:formation2();break;
            case 3:formation3();break;
            case 4:formation4();break;
            default:break;
        }
    }
    //主要利用这个函数调用不同的设置阵型的函数
```

### 四、Beings文件夹内代码介绍

##### BeingsImage.java

```
public enum BeingsImage {
    Brother1("/Red_1.jpg"), Brother2("/Orange_2.jpg"), Brother3("/Yellow_3.jpg"),Brother4("/Green_4.jpg"),
    Brother5("/Gray_5.jpg"),Brother6("/Blue_6.jpg"),Brother7("/purple_7.jpg"),Snake_one("/snake.jpg"),
    Leader_one("/xiezijing.jpg"),Soldier_one("/xiezi.jpg"),Grandpa_one("/grandpa.jpg"),
    BrotherDie("/Brother_die.jpg"),GrandpaDie("/grandpa_die.jpg"),SoldierDie("/xiezi_die.jpg"),
    SnakeDie("/snake_die.jpg"),LeaderDie("/xiezijing_die.jpg");

    private final String imageURL;
    private final Image image;

    BeingsImage(String imageURL) {
        this.imageURL = imageURL;
        this.image = new Image(this.getClass().getResource(imageURL).toString());
    }
    public Image getImage(){
        return image;
    }
}
//枚举类型，将图片导入进来
```
###### Creature.java

```
//储存生物的信息：
    protected int life=0;
    protected int party=1;//1 for good 2 for bad
    protected int number;
    protected String name;
    protected int x;
    protected int y;
    protected ImageView imageView;
//对外的主要接口：
    public void setImageView()；
    //获得生物活着的Image，赋值给imageView
    public void setDieView()；
    //获得生物死了的Image，赋值给imageView
    public void move(int to_x,int to_y)；
    //move操作，修改生物的位置信息(x,y)
    
```


### 五、GUI文件夹内代码介绍

##### Brother.java
其他类基本类似

```
public class BrotherThread  implements Runnable{
    BattleField battleField;
    Creature brother;
    public BrotherThread(Creature brother,BattleField battleField){
        System.out.println("Init BrotherThread:"+brother.get_name());
        this.brother=brother;
        this.battleField=battleField;
    }
    private boolean moveForward(){//如果生物还活着，就去尝试move
        if(brother.get_life()==0)
            return false;
        boolean a=this.battleField.move(brother.get_x(),brother.get_y(),brother.get_x()+1,brother.get_y());
        return a;
    }
    public void run(){//实现run的接口
        if(!battleField.getIsRecord()) {
            while (!battleField.getIsEnd()) {//如果没有结束，就去尝试move
                try {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    moveForward();
                } catch (Exception e) {
                }
            }
        }
    }
}
```

### 六、图形界面代码介绍

##### Main.java

主要实现键盘操作，以及图形界面的输出化，以及整个项目的开始接口。

##### Controller.java

根据键盘输入情况，对BattleField的对象实例化，并初始化图像信息，设置各种标志，进行战斗。

### 七、效果显示

![Image](https://github.com/zfq005/Final-Project/raw/master/%E5%BC%A0%E4%B8%B0%E7%AD%BE-161220165/TestBattle.gif)
