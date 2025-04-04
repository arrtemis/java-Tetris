package tetris;

public class GameThread extends Thread{
  private GameArea ga;
  private GameFrame gf;

  private int score;
  private int level = 1;
  private int scorePerLevel = 3;
  private int pausePerLevel = 100;
  private int pause = 1000;
  public GameThread(GameArea ga, GameFrame gf){
    this.ga = ga;
    this.gf = gf;
    gf.updateScore(score);
    gf.updateLevel(level);
  }
  @Override
  public void run(){
    while(true){
      ga.spawnBlock();
      while(ga.moveBlockDown()){
        try {
          Thread.sleep(pause);
        } catch (InterruptedException e) {
          return;
        }
      }
      if(ga.isOutOfBounds()){
        Tetris.gameOver(score);
        break;
      }
      ga.moveBlockToBackground();
      score += ga.clearLines();
      gf.updateScore(score);
      int lvl = score / scorePerLevel + 1;
      if(lvl > level){
        level = lvl;
        pause -= pausePerLevel;
        gf.updateLevel(level);
      }
    }
  }
}
