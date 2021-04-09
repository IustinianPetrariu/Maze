package sample.mazegenerators;

public class KruskalGenerator extends MazeGeneratorRunnable implements MazeGenerator {
    private Maze maze;

    @Override
    public void getMaze(Maze maze) {
        this.maze = maze;
    }

    @Override
    public void generateMaze() {

    }
}
