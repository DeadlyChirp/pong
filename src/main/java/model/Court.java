package model;

public class Court {
    // instance parameters
    private final RacketController playerA, playerB;
    private final double width, height; // size of the application
    private double racketSpeed = 300.0; // m/s
    private double racketSize = 100.0; // m
    private double ballRadius = 10.0; // ball radius/ size
    // instance state
    private double racketA; // playerOnePos.Y
    private double racketB; // playerOnePos.Y
    private double ballX, ballY; // position de la balle
    private double ballSpeedX =1;
    private double ballSpeedY =1; // declare to 1

    //need player Height about 100
    private static final int Player_Height = 100;

    //need player width about 15
    private static final int Player_Width = 15;
    //score p1 p2
//    private static int scoreP1 = 0;
//    private static int scoreP2 = 0;
    private final int racketThickness = 10;



    //TEST AVEC SAMI ISMA<3
    public Score score; //score class
    public Court(RacketController playerA, RacketController playerB, double width, double height) {
        this.playerA = playerA;
        this.playerB = playerB;
        this.width = width;
        this.height = height;
        this.score = new Score();
        reset();
    }

    public double getWidth() {
        return width;
    }

    public double getHeight() {
        return height;
    }

    public double getRacketSize() {
        return racketSize;
    }

    public double getRacketA() {
        return racketA;
    }

    public double getRacketB() {
        return racketB;
    }

    public double getBallX() {
        return ballX;
    }

    public double getBallY() {
        return ballY;
    }




    public void update(double deltaT) {

        switch (playerA.getState()) {
            case GOING_UP:
                racketA -= racketSpeed * deltaT; //la vitesse de la racket * delta
                if (racketA < 0.0) racketA = 0.0; //position racket Y axis 0 cao nhat
                break;
            case IDLE:
                break;
            case GOING_DOWN:
                racketA += racketSpeed * deltaT;
                if (racketA + racketSize > height) racketA = height - racketSize; //
                break;
        }
        switch (playerB.getState()) {
            case GOING_UP:
                racketB -= racketSpeed * deltaT;
                if (racketB < 0.0) racketB = 0.0;
                break;
            case IDLE:
                break;
            case GOING_DOWN:
                racketB += racketSpeed * deltaT;
                if (racketB + racketSize > height) racketB = height - racketSize;
                break;
        }
        if (updateBall(deltaT)) reset();
    }


    /**
     * @return true if a player lost
     */
    private boolean updateBall(double deltaT) {
        // first, compute possible next position if nothing stands in the way
        double nextBallX = ballX + deltaT * ballSpeedX;
        double nextBallY = ballY + deltaT * ballSpeedY;
        // next, see if the ball would meet some obstacle
        if (nextBallY < 0 || nextBallY > height) {
            ballSpeedY = -ballSpeedY; //rebondir
            nextBallY = ballY + deltaT * ballSpeedY;
//            nextBallY = ballY + deltaT * ballSpeedY + ((ballY < 0)?(-100*Math.random()):(100*Math.random())); //eviter que la balle depasse en haut ou en bas
        } //? = if
        /*-------------------------------------------------------------------------width
        *
        *
        *
        * -racket A tete
        *
        *
        *
        *
        * -racket A + racket Size bottom of the racket
        *
        *
        * y */
        if ((nextBallX < 0 && nextBallY > racketA && nextBallY < racketA + racketSize) //
                || (nextBallX > width && nextBallY > racketB && nextBallY < racketB + racketSize)) {
            ballSpeedX = -ballSpeedX;  //rebondir ball x balle tombe dans la raquette
            nextBallX = ballX + deltaT * ballSpeedX;
        //nextball = new vitesse next position
        } else if (nextBallX < 0) { //if en haut une des condition est fausse, balle a pas touche la raquette
            //player 1

            //SFX SOUND ???

//            try {
//                AudioClip clip = Applet.newAudioClip(
//                        new URL("/home/pain/Downloads/Paddle-sfx.wav"));
//                clip.play();
//            } catch (MalformedURLException murle) {
//                System.out.println(murle);
//            }

            score.addScore1();
            return true;
        } else if (nextBallX > width) { //player 2

            score.addScore2();
            //racket size reduce size if loose
             return true;

        }
       //increase speed of the ball
// BIG PROBLEM IN THE MIDDLE AUTO RESET THE GAME
//        if( ((ballX + ballRadius > racketA) && ballY >= racketB && ballY <= racketB + racketSize) ||
//                ((ballX < racketA + racketThickness) && ballY >= racketA && ballY <= racketA + racketThickness)) {
//        ballSpeedY += 1 * Math.signum(ballSpeedY);
//        ballSpeedX += 1 * Math.signum(ballSpeedX);
//        ballSpeedY *= -1;
//        ballSpeedY *= -1;
//    }
        //la ga fait rebondir la balle
        ballX = nextBallX;
        ballY = nextBallY;
        return false;
}
    public double getBallRadius() {
        return ballRadius;
    }

    void reset() {
        this.racketA = height / 2;
        this.racketB = height / 2;
        this.ballSpeedX = 200.0;
        this.ballSpeedY = 200.0;
        this.ballX = width / 2;
        this.ballY = height / 2;
    }
}
