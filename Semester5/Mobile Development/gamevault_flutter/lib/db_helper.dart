import 'package:gamevault_flutter/game.dart';
import 'package:sqflite_common_ffi/sqflite_ffi.dart';

class DatabaseHelper {
  static final DatabaseHelper _databaseHelper = DatabaseHelper._();

  DatabaseHelper._();

  factory DatabaseHelper() {
    return _databaseHelper;
  }

  Future<void> createTables(Database database) async {
    await database.execute("""CREATE TABLE games (
          id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
          title TEXT NOT NULL,
          description TEXT NOT NULL,
          genre TEXT NOT NULL,
          progress REAL NOT NULL,
          rating REAL NOT NULL,
          hoursPlayed INTEGER NOT NULL
      )""");
  }

  Future<void> dropTable(Database database) async {
    await database.execute("""DROP TABLE games""");
  }

  Future<Database> databaseInit() async {
    // // Initialize sqflite_ffi databaseFactory
    // sqfliteFfiInit();

    // // Set the databaseFactory to databaseFactoryFfi
    // databaseFactory = databaseFactoryFfi;

    return await openDatabase("game_vault.db", version: 1,
        onCreate: (Database database, int version) async {
          await createTables(database);
        },
        // onOpen: (Database database) async {
        //   await dropTable(database);
        //   await createTables(database);
        // } ,
    );
  }

  Future<int> createGame(Game game) async {
    final database = await _databaseHelper.databaseInit();
    int result = await database.insert('games', game.toMap());
    return result;
  }

  Future<int> updateGame(Game game) async {
    final database = await _databaseHelper.databaseInit();
    print(game.id);
    int result = await database
        .update('games', game.toMap(), where: "id = ?", whereArgs: [game.id]);
    print(result);
    return result;
  }

  Future<List<Game>> retrieveGames() async {
    final database = await _databaseHelper.databaseInit();
    final List<Map<String, dynamic>> map = await database.query('games', orderBy: 'id');
    return List.generate(map.length, (index) => Game.fromMap(map[index]));
  }

  Future<void> deleteGame(int id) async {
    final database = await _databaseHelper.databaseInit();
    await database.delete('games', where: "id = ?", whereArgs: [id]);
  }

  Future<Game?> getGameById(int id) async {
    final database = await _databaseHelper.databaseInit();
    List<Map<String, dynamic>> result = await database.query('games', where: "id = ?", whereArgs: [id]);
    return result.isNotEmpty ? Game(id: result[0]['id'],title: result[0]['title'], description: result[0]['description'], genre: result[0]['genre'], progress: result[0]['progress'], rating: result[0]['rating'], hoursPlayed: result[0]['hoursPlayed']) : null;
  }
}
