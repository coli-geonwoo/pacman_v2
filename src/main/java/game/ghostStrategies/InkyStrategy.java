package game.ghostStrategies;

import game.Game;
import game.GameplayPanel;
import game.entities.ghosts.Ghost;
import game.utils.Utils;

//Stratégie concrète d'Inky (le fantôme bleu)
public class InkyStrategy implements IGhostStrategy{
    private Ghost otherGhost;
    public InkyStrategy(Ghost ghost) {
        this.otherGhost = ghost;
    }

    //잉키는 블링키의 위치를 ​​이용해 팩맨을 타겟으로 삼습니다. 블링키의 위치와 팩맨 앞의 한 칸 사이의 벡터를 구하고, 이 벡터를 팩맨 앞의 한 칸 위치에 더해 ​​잉키의 타겟을 구합니다.
    @Override
    public int[] getChaseTargetPosition() {
        int[] position = new int[2];
        int[] pacmanFacingPosition = Utils.getPointDistanceDirection(Game.getPacman().getxPos(), Game.getPacman().getyPos(), 32d, Utils.directionConverter(Game.getPacman().getDirection()));
        double distanceOtherGhost = Utils.getDistance(pacmanFacingPosition[0], pacmanFacingPosition[1], otherGhost.getxPos(), otherGhost.getyPos());
        double directionOtherGhost = Utils.getDirection(otherGhost.getxPos(), otherGhost.getyPos(), pacmanFacingPosition[0], pacmanFacingPosition[1]);
        int[] blinkyVectorPosition = Utils.getPointDistanceDirection(pacmanFacingPosition[0], pacmanFacingPosition[1], distanceOtherGhost, directionOtherGhost);
        position[0] = blinkyVectorPosition[0];
        position[1] = blinkyVectorPosition[1];
        return position;
    }

    //En pause, Inky cible la case en bas à droite
    //일시 정지 시 Inky는 오른쪽 하단 사각형을 타겟팅합니다.
    @Override
    public int[] getScatterTargetPosition() {
        int[] position = new int[2];
        position[0] = GameplayPanel.width;
        position[1] = GameplayPanel.height;
        return position;
    }
}
