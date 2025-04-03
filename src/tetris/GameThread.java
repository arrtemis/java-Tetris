package tetris;

public class GameThread extends Thread{
  private GameArea ga;
  public GameThread(GameArea ga){
    this.ga = ga;
  }
  @Override
  public void run(){
    while(true){
      try {
        ga.moveBlockDown();
        Thread.sleep(1000);
      } catch (InterruptedException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }
    }

  }
}
