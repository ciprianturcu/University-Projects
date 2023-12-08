import 'package:flutter/material.dart';
import 'package:gamevault_flutter/game.dart';
import 'package:gamevault_flutter/game_repository.dart';
import 'package:logger/logger.dart';

class GameViewModel extends ChangeNotifier {
  final GameRepository _gameRepository = GameRepository();
  final Logger _log = Logger();

  List<Game> _games = [];

  List<Game> get games => _games;

  GameViewModel() {
    loadGames();
  }

  Future<void> loadGames() async {
    try {
      _log.d('fetching games to viewmodel');
      _games = await _gameRepository.retrieveGames();
      notifyListeners();
      _log.d(
          'successfully fetched games in viewmodel, nr of games : ${games.length}');
    } catch (e) {
      _log.e('error fetching games to viewmodel : $e');
      rethrow;
    }
  }

  Future<void> addGame(Game game) async {
    try {
      _log.d('adding game in viewmodel');
      int id = await _gameRepository.createGame(game);
      game.id=id;
      await loadGames();
      _log.d('game added sucessfully in viewmodel : $game');
    } catch (e) {
      _log.e('error adding game in viewmodel : $e');
      rethrow;
    }
  }

  Future<void> updateGame(Game game) async {
    try {
      _log.d('updating game in viewmodel : $game');
      await _gameRepository.updateGame(game);
      await loadGames();
      _log.d('succesfully updated game in viewmodel : $game');
    } catch (e) {
      _log.e('error updating game in viewmodel : $game, $e');
      rethrow;
    }
  }

  Future<void> deleteGame(int id) async {
    try {
      _log.d('deleting game with id in viewmodel : $id');
      await _gameRepository.deleteGame(id);
      await loadGames();
      _log.d('sucessfully deleted game with id in viewmodel : $id');
    } catch (e) {
      _log.e('error when trying to delete game with id in viewmodel : $id, $e');
      rethrow;
    }
  }

  Future<Game?> getGameById(int id) async {
    try {
      _log.d('retrieving game by id in viewmodel');
      return await _gameRepository.getGameById(id);
    } catch (e) {
      _log.e('error retrieving game by id in viewmodel : $id , $e');
      rethrow;
    }
  }
}
