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



### 三、Battle文件夹内代码介绍

### 四、Beings文件夹内代码介绍

### 五、GUI文件夹内代码介绍

### 六、图形界面代码介绍

### 七、效果显示

