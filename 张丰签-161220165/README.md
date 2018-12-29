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

### 四、Beings文件夹内代码介绍

### 五、GUI文件夹内代码介绍

### 六、图形界面代码介绍

### 七、效果显示

