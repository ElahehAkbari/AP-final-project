///*** In The Name of Allah ***/
//package game.template.doublebuffering;
//import game.template.bufferstrategy.GameState;
//
//import java.util.PrimitiveIterator;
//import java.util.Random;
//
///**
// * A very simple structure for the main game loop.
// * THIS IS NOT PERFECT, but works for most situations.
// * Note that to make this work, none of the 2 methods
// * in the while loop (update() and render()) should be
// * long running! Both must execute very quickly, without
// * any waiting and blocking!
// *
// * Detailed discussion on different game-loop design-patterns
// * is available in the following link:
// *    http://gameprogrammingpatterns.com/game-loop.html
// */
//public class GameLoop implements Runnable {
//
//    /**
//     * Frame Per Second.
//     * Higher is better, but any value above 24 is fine.
//     */
//    public static final int FPS = 30;
//
//    private GameCanvas canvas;
//    private GameState state;
//    private String type;
//    private String timeType;
//
//    public GameLoop(GameCanvas gc, String type, String timeType) {
//        this.type = type;
//        this.timeType = timeType;
//        canvas = gc;
//    }
//
//    public void init() {
//        //
//        // Perform all initializations ...
//        //
//        state = new GameState(type, timeType);
////        canvas.addKeyListener(state.getKeyListener());
//        canvas.addMouseListener(state.getMouseListener());
//        canvas.addMouseMotionListener(state.getMouseMotionListener());
//        canvas.requestFocusInWindow();
//    }
//
//    @Override
//    public void run() {
//        boolean gameOver = state.isGameOver();
//        System.out.println(gameOver);
////        boolean gameOver = false;
//        while (!gameOver) {
//            try {
//                long start = System.currentTimeMillis();
//                //
//                state.update();
//                canvas.render(state);
//                //
//                long delay = (1000 / FPS) - (System.currentTimeMillis() - start);
//                if (delay > 0)
//                    Thread.sleep(delay);
//            } catch (InterruptedException ex) {
//            }
//        }
//
//    }
//}
