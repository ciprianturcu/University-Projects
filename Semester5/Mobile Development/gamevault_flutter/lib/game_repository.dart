import 'package:gamevault_flutter/db_helper.dart';
import 'package:gamevault_flutter/game.dart';
import 'package:logger/logger.dart';

class GameRepository {
  final DatabaseHelper _gameDatabase = DatabaseHelper();

  static final _log = Logger();

  Future<List<Game>> retrieveGames() async {
    try {
      final dbClient = await _gameDatabase.database;
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
      final dbClient = await _gameDatabase.database;
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
              hoursPlayed: result[0]['hoursPlayed'])
          : null;
    } catch (e) {
      _log.e('error getting game by id : $id, $e');
      rethrow;
    }
  }
  
  Future<int> createGame(Game game) async {
    try {
      final dbClient = await _gameDatabase.database;
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
      final dbClient = await _gameDatabase.database;
      _log.d('deleting game with id : $id');
      await dbClient.delete('games', where: "id = ?", whereArgs: [id]);
    } catch (e) {
      _log.e('error deleting game on id : $id');
      rethrow;
    }
  }

  Future<int> updateGame(Game game) async {
    try {
      final dbClient = await _gameDatabase.database;
      _log.d('updating game : ${game.id}');
      int result = await dbClient
          .update('games', game.toMap(), where: "id = ?", whereArgs: [game.id]);
      return result;
    } catch (e) {
      _log.e('error updating game : ${game.id}, $e');
      rethrow;
    }
  }
}
