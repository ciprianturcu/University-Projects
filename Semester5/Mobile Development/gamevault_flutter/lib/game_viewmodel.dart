import 'dart:async';

import 'package:flutter/material.dart';
import 'package:gamevault_flutter/game.dart';
import 'package:gamevault_flutter/game_repository.dart';
import 'package:logger/logger.dart';

class GameViewModel extends ChangeNotifier {
  final GameRepository _gameRepository = GameRepository();
  final Logger _log = Logger();

  List<Game> _games = [];
  bool _serverStatus = false;

  List<Game> get games => _games;
  bool get serverStatus => _serverStatus;

  Timer? _serverStatusTimer;

  GameViewModel() {
    loadGames();
    _startServerStatusCheck();
  }

  Future<void> loadGames() async {
    try {
      _log.d('fetching games to viewmodel');
      _games = await _gameRepository.retrieveGames();
      _serverStatus = _gameRepository.serverStatus;
      notifyListeners();
      _log.d(
          'successfully fetched games in viewmodel, nr of games : ${games.length}');
    } catch (e) {
      _log.e('error fetching games to viewmodel : $e');
      rethrow;
    }
  }

  void _startServerStatusCheck() {
    _log.d("starting periodic check of changes");
    const checkInterval = Duration(seconds: 10);
    _serverStatusTimer = Timer.periodic(checkInterval, (timer) async {
      _log.d("checking if something changed");
      // Store the previous values
      List<Game> previousGames = _games;
      bool previousServerStatus = _serverStatus;

      await _gameRepository.handleServerStatusChanges();
      _games = await _gameRepository.retrieveGames();
      _serverStatus = _gameRepository.serverStatus;

      // Check if there's a change in games or server status
      bool gamesChanged = !listEquals(previousGames, _games);
      bool serverStatusChanged = previousServerStatus != _serverStatus;

      // Notify listeners if there's a change
      if (gamesChanged || serverStatusChanged) {
        _log.d("notifing listners due to gamesChange:$gamesChanged, statusServerChange $serverStatusChanged");
        notifyListeners();
      }
    });
  }

// Helper function to check list equality
  bool listEquals(List<Game> a, List<Game> b) {
    if (identical(a, b)) {
      return true;
    }
    if (a.length != b.length) {
      return false;
    }
    for (int i = 0; i < a.length; i++) {
      if (a[i] != b[i]) {
        return false;
      }
    }
    return true;
  }

  Future<void> addGame(Game game) async {
    try {
      _log.d('adding game in viewmodel');
      await _gameRepository.handleInsertRequest(game);
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
      await _gameRepository.handleUpdateRequest(game);
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
      await _gameRepository.handleDeleteRequest(id);
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

  Future<bool> getSyncStatusByGameId(int id) async {
    try {
      return await _gameRepository.checkSyncedWithServerByGameId(id);
    } catch (e) {
      _log.e("error occured while trying to get sync status of game with id: $id");
      return false;
    }
  }

  @override
  void dispose() {
    _serverStatusTimer?.cancel();
    super.dispose();
  }
}
