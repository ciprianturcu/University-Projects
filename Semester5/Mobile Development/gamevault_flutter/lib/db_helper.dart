import 'package:gamevault_flutter/game.dart';
import 'package:logger/logger.dart';
import 'package:sqflite_common_ffi/sqflite_ffi.dart';

class DatabaseHelper {
  static Database? _database;
  static final _log = Logger();

  Future<void> changeDatabaseContentWhenOnlineServer(
      List<Game> gamesFromServer) async {
    await clearDatabase();
    await addGamesFromServerToDatabase(gamesFromServer);
  }

  Future<void> addGamesFromServerToDatabase(List<Game> gamesFromServer) async {
    try {
      final dbClient = await database;
      for (Game game in gamesFromServer) {
        try {
          await dbClient.insert('games', game.toMap());
        } catch (e) {
          _log.e("error inserting server game into database, error: $e");
        }
      }
    } catch (e) {
      _log.e("error processing games from server into database, error: $e");
    }
  }

  Future<Database> get database async {
    if (_database != null) return _database!;
    _database = await databaseCreation();
    return _database!;
  }

  Future<void> clearDatabase() async {
    try {
      final Database db = await database;
      int count = await db.rawDelete('DELETE FROM games');
      _log.d("cleared $count games from database");
    } catch (e) {
      _log.e("error clearing database , error: $e");
    }
  }

  Future<void> createTables(Database database) async {
    try {
      _log.d('creating table for database');
      await database.execute("""CREATE TABLE games (
          id INTEGER PRIMARY KEY NOT NULL,
          title TEXT NOT NULL,
          description TEXT NOT NULL,
          genre TEXT NOT NULL,
          progress REAL NOT NULL,
          rating REAL NOT NULL,
          hoursPlayed INTEGER NOT NULL,
          isSyncedWithServer INTEGER NOT NULL
      )""");
    } catch (e) {
      _log.e('error creating table : $e');
      rethrow;
    }
  }

  Future<Database> databaseCreation() async {
    try {
      return await openDatabase(
        "game_vault.db", version: 1,
        onCreate: (Database database, int version) async {
          await createTables(database);
        },
      );
    } catch (e) {
      _log.e('error initializing database : $e');
      rethrow;
    }
  }

  Future<List<Game>> retrieveGames() async {
    try {
      final dbClient = await database;
      _log.d('retrieving games from database');
      final List<Map<String, dynamic>> map =
          await dbClient.query('games', orderBy: 'id');
      return List.generate(map.length, (index) => Game.fromMap(map[index]));
    } catch (e) {
      _log.e('error retrieving games : $e');
      rethrow;
    }
  }

  Future<Game?> getGameById(int id) async {
    try {
      final dbClient = await database;
      _log.d('fetching game with id : $id');
      List<Map<String, dynamic>> result =
          await dbClient.query('games', where: "id = ?", whereArgs: [id]);
      return result.isNotEmpty
          ? Game(
              id: result[0]['id'],
              title: result[0]['title'],
              description: result[0]['description'],
              genre: result[0]['genre'],
              progress: result[0]['progress'],
              rating: result[0]['rating'],
              hoursPlayed: result[0]['hoursPlayed'],
              isSyncedWithServer: result[0]['isSyncedWithServer'])
          : null;
    } catch (e) {
      _log.e('error getting game by id : $id, $e');
      rethrow;
    }
  }

  Future<bool> getIsSyncedWithServerByGameId(int id) async {
    try {
      final dbClient = await database;
      _log.d("fetching sync status of game with id : $id");
      List<Map<String, dynamic>> result =
          await dbClient.query('games', where: "id = ?", whereArgs: [id]);
      if (result[0]['isSyncedWithServer'] == 1) return true;
      return false;
    } catch (e) {
      _log.e("error in getting sync status of game");
      rethrow;
    }
  }

  Future<List<Map<String, dynamic>>> getUnsyncedGames() async {
    try {
      final dbClient = await database;
      List<Map<String, dynamic>> unsyncedGameList = await dbClient.query(
        'games',
        where: 'isSyncedWithServer = ?',
        whereArgs: [0], // 0 means the game is not synced with the server
      );
      return unsyncedGameList;
    } catch (e) {
      _log.e("error getting unsynced games from database");
      return List.empty();
    }
  }

  Future<int> insertGame(Game game) async {
    try {
      final dbClient = await database;
      _log.d('creating game : ${game.title}');
      int result = await dbClient.insert('games', game.toMap());
      return result;
    } catch (e) {
      _log.e('error creating game : ${game.id}, ${game.title}, $e');
      rethrow;
    }
  }

  Future<void> deleteGame(int id) async {
    try {
      final dbClient = await database;
      _log.d('deleting game with id : $id');
      await dbClient.delete('games', where: "id = ?", whereArgs: [id]);
    } catch (e) {
      _log.e('error deleting game on id : $id');
      rethrow;
    }
  }

  Future<int> updateGame(Game game) async {
    try {
      final dbClient = await database;
      _log.d('updating game : ${game.id}');
      int result = await dbClient
          .update('games', game.toMap(), where: "id = ?", whereArgs: [game.id]);
      return result;
    } catch (e) {
      _log.e('error updating game : ${game.id}, $e');
      rethrow;
    }
  }

  Future<void> bulkInsert(List<Game> games) async {
    try {
      final dbClient = await database;
      List<int> idsAdded = List.empty();
      for (Game game in games) {
        idsAdded.add(await dbClient.insert('games', game.toMap()));
      }
      _log.d("bulk creation of games successful, ids added : $idsAdded");
    } catch (e) {
      _log.d("error in bulk creation of games");
      rethrow;
    }
  }

    Future<void> close() async {
    try {
      final db = await database;
      _log.d('closing database');
      await db.close();
    } catch (e) {
      _log.e('error closing database: $e');
    }
  }
}
