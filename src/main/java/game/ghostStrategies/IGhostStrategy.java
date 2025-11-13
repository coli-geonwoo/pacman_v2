package game.ghostStrategies;

//Interface pour décrire les stratégies des différents fantômes (cette vidéo les explique bien : https://www.youtube.com/watch?v=ataGotQ7ir8)
//다양한 유령의 전략을 설명하는 인터페이스(다음 영상에서 자세히 설명합니다: https://www.youtube.com/watch?v=ataGotQ7ir8)
public interface IGhostStrategy {
    int[] getChaseTargetPosition(); //Case ciblée lorsque le fantôme poursuit Pacman
    int[] getScatterTargetPosition(); //Case ciblée lorsque le fantôme fait une pause
}
