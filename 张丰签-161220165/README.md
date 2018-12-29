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
    
    public Creature getCreature(int num);
    
    public Formation getFormation();
    
    public void setFormation(int num);
    
    public int getFormationType();
    
    public BattleRecord[] getRecord();
    
    public int getRecordLength();
    
    public void setRecordLength(int num);
    
    public boolean getIsEnd();
    
    public boolean getIsRecord();
    
    public void setIsRecord(boolean value);
    
    public void setIsEnd(boolean value);
    
    public void show_all();
    
    public boolean set_one(int x,int y,int num);
    
    private boolean BrotherMove(int num,int x,int y);
    
    private boolean GrandpaMove(int x,int y);
    
    private boolean SoldierMove(int num,int x,int y);
    
    public boolean move(int x,int y,int to_x,int to_y);
    
    private int RandomAttack(int num);
    
    private void BadBattle(int src,int dst);
    
    private void GoodBattle(int src,int dst);
    
    private void set_end();
    
    public void initFormation();
    
    public void setImage();
    
    public void writerecord();
    
    public void RecordPlay(int i);
    

```

### 四、Beings文件夹内代码介绍

### 五、GUI文件夹内代码介绍

### 六、图形界面代码介绍

### 七、效果显示

