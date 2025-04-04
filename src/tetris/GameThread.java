package tetris;

public class GameThread extends Thread{
  private GameArea ga;
  private GameFrame gf;
  private int score;
  public GameThread(GameArea ga, GameFrame gf){
    this.ga = ga;
    this.gf = gf;
  }
  @Override
  public void run(){
    while(true){
      ga.spawnBlock();
      while(ga.moveBlockDown()){
        try {
          Thread.sleep(500);
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
      }
      if(ga.isOutOfBounds()){
        System.out.println("Game Over");
        break;
      }
      ga.moveBlockToBackground();
      score += ga.clearLines();
      gf.updateScore(score);
    }
  }
}
