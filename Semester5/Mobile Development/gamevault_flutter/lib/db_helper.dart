import 'package:logger/logger.dart';
import 'package:sqflite_common_ffi/sqflite_ffi.dart';

class DatabaseHelper {
  static Database? _database;
  static final _log = Logger();

  Future<Database> get database async {
    if (_database != null) return _database!;
    _database = await databaseInit();
    return _database!;
  }

  Future<void> createTables(Database database) async {
    try {
      _log.d('creating table for database');
      await database.execute("""CREATE TABLE games (
          id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
          title TEXT NOT NULL,
          description TEXT NOT NULL,
          genre TEXT NOT NULL,
          progress REAL NOT NULL,
          rating REAL NOT NULL,
          hoursPlayed INTEGER NOT NULL
      )""");
    } catch (e) {
      _log.e('error creating table : $e');
      rethrow;
    }
  }

  Future<void> dropTable(Database database) async {
    try {
      _log.d('deleteing table from database');
      await database.execute("""DROP TABLE games""");
    } catch (e) {
      _log.e('error deleting table : $e');
      rethrow;
    }
  }

  Future<Database> databaseInit() async {
    try {
      // // Initialize sqflite_ffi databaseFactory
      // sqfliteFfiInit();

      // // Set the databaseFactory to databaseFactoryFfi
      // databaseFactory = databaseFactoryFfi;

      return await openDatabase(
        "game_vault.db", version: 1,
        onCreate: (Database database, int version) async {
          await createTables(database);
        },
        // onOpen: (Database database) async {
        //   await dropTable(database);
        //   await createTables(database);
        // } ,
      );
    } catch (e) {
      _log.e('error initializing database : $e');
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
