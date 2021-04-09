package sample.mazegenerators;

public class PrimGenerator extends MazeGeneratorRunnable implements MazeGenerator {
    private Maze maze;

    @Override
    public void getMaze(Maze maze) {
        this.maze = maze;
    }

    @Override
    public void generateMaze() {

    }
}
