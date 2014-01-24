package com.roundtriangles.games.zaria.services.db;


//public class DatabaseServiceTest {
//
//    private final static String DATABASE_URL = "jdbc:h2:mem:account";
//
//    private Dao<SingleGame, Integer> gameDao;
//    private Dao<GamePiece, Integer> pieceDao;
//    private Dao<GameLogEntry, Integer> logDao;
//
//    @Test
//    public void testSimle() {
//        ConnectionSource connectionSource = null;
//        try {
//            // create our data source
//            connectionSource = new JdbcConnectionSource(DATABASE_URL);
//            // setup our database and DAOs
//            setupDatabase(connectionSource);
//            // read and write some data
//            readWriteData();
//        } catch (Exception e) {
//            Assert.fail(e.getMessage());
//        } finally {
//            // destroy the data source which should close underlying connections
//            if (connectionSource != null) {
//                connectionSource.closeQuietly();
//            }
//        }
//    }
//
//    /**
//     * Setup our database and DAOs
//     */
//    private void setupDatabase(ConnectionSource connectionSource) throws Exception {
//
//        gameDao = DaoManager.createDao(connectionSource, SingleGame.class);
//        pieceDao = DaoManager.createDao(connectionSource, GamePiece.class);
//        logDao = DaoManager.createDao(connectionSource, GameLogEntry.class);
//
//        // if you need to create the table
//        TableUtils.createTable(connectionSource, SingleGame.class);
//        TableUtils.createTable(connectionSource, GamePiece.class);
//        TableUtils.createTable(connectionSource, GameLogEntry.class);
//    }
//
//    private void readWriteData() throws Exception {
//        // create an instance of Account
//        SingleGame game = new SingleGame();
//        game.name = "Game1";
//        game.turn = Team.BLACK;
//        game.dim = 13;
//        game.created = new Date();
//        game.updated = new Date();
//
//        gameDao.create(game);
//
//        GamePiece piece = new GamePiece();
//        piece.game = game;
//        piece.team = Team.BLACK;
//        piece.x = 0;
//        piece.y = 0;
//
//        pieceDao.create(piece);
//
//        GamePiece piece2 = new GamePiece();
//        piece2.game = game;
//        piece2.team = Team.WHITE;
//        piece2.x = 10;
//        piece2.y = 10;
//
//        pieceDao.create(piece2);
//
//        GameLogEntry logEntry = new GameLogEntry();
//        logEntry.game = game;
//        logEntry.piece = piece;
//        logEntry.x1 = 1;
//        logEntry.y1 = 0;
//        logEntry.x2 = 0;
//        logEntry.y2 = 0;
//
//        logDao.create(logEntry);
//
//        SingleGame result = gameDao.queryForId(game._id);
//
//        CloseableIterator<GamePiece> iterator = result.pieces.closeableIterator();
//
//        Assert.assertTrue(iterator.hasNext());
//        GamePiece pieceResult = iterator.next();
//        Assert.assertEquals(Team.BLACK, pieceResult.team);
//        iterator.closeQuietly();
//
//        List<GameLogEntry> log = logDao.queryForAll();
//        Assert.assertEquals(1, log.size());
//    }
//
//}
