import 'dart:async';
import 'dart:convert';
import 'package:gamevault_flutter/db_helper.dart';
import 'package:gamevault_flutter/game.dart';
import 'package:gamevault_flutter/ws_helper.dart';
import 'package:logger/logger.dart';
import 'package:http/http.dart' as http;

class GameRepository {
  final DatabaseHelper _gameDatabase = DatabaseHelper();

  static final _log = Logger();

  static const String _ip = "192.168.100.6:8000";

  bool _serverStatus = false;
  bool get serverStatus => _serverStatus;

  final String _generalEndpoint = 'http://$_ip/api/games/';
  final String _updateDeleteEndpoint = 'http://$_ip/api/game/';

  late final WebSocketHelper _webSocketHelper;

  GameRepository() {
    _webSocketHelper = WebSocketHelper(
      onDelete: _deleteGameIntoDatabase,
      onCreate: (gameData) =>
          _insertGameIntoDatabase(gameData),
      onUpdate: (gameData) =>
          _updateGameIntoDatabase(gameData),
      onBulkCreate: (gameDataList) => _bulkInsertIntoDatabase(gameDataList),
    );
    initRepositoryConnections();
  }

  Future<void> initRepositoryConnections() async {
    await handleServerStatusChanges();
    _webSocketHelper.connectWebSocket();
  }

  Future<void> handleServerStatusChanges() async {
    if (await _isServerOnline()) {
      if (_serverStatus == false) {
        _serverStatus = true;
        await sendUnsyncedGamesToServer();
        await fetchServerDataAndReplaceLocal();
      }
      return;
    }
    _serverStatus = false;
  }

  Future<bool> _isServerOnline() async {
    try {
      final response = await http.get(Uri.parse(_generalEndpoint));
      return response.statusCode == 200;
    } catch (e) {
      _log.e("error in server online check : $e");
      return false;
    }
  }

  Future<void> fetchServerDataAndReplaceLocal() async {
    try {
      final response = await http.get(Uri.parse(_generalEndpoint));
      if (response.statusCode == 200) {
        List<Game> responseGameList = Game.parseGames(response.body);
        await _gameDatabase
            .changeDatabaseContentWhenOnlineServer(responseGameList);
      }
    } catch (e) {
      _log.e("error geting data from server and replacing local : $e");
    }
  }

  Future<List<Game>> retrieveGames() async {
    try {
      return await _gameDatabase.retrieveGames();
    } catch (e) {
      rethrow;
    }
  }

  Future<Game?> getGameById(int id) async {
    try {
      return await _gameDatabase.getGameById(id);
    } catch (e) {
      rethrow;
    }
  }

  Future<void> handleInsertRequest(Game game) async {
    try {
      final response = await http.post(
        Uri.parse(_generalEndpoint),
        headers: <String, String>{
          'Content-Type': 'application/json; charset=UTF-8',
        },
        body: jsonEncode(game.toMapPublicDetails()),
      );

      if (response.statusCode == 201) {
        _log.d('POST request successful: ${response.body}');
        _log.d('inserting game into database with synced true');
        game = Game.fromMapServerTemplate(jsonDecode(response.body));
        await _insertGameIntoDatabase(game);
        return;
      }
    } catch (e) {
      _log.e("error on insert request : $e");
      _insertGameIntoDatabase(game);
    }
  }

  Future<void> handleUpdateRequest(Game game) async {
    try {
      final response = await http.put(
        Uri.parse('$_updateDeleteEndpoint${game.id}/'),
        headers: <String, String>{
          'Content-Type': 'application/json; charset=UTF-8',
        },
        body: jsonEncode(game.toMapPublicDetails()),
      );
      if (response.statusCode == 200) {
        _log.d('PUT request successful: ${response.body}');
        _log.d('updating game into database with synced true');
        _log.d(response.body);
        game = Game.fromMapServerTemplate(jsonDecode(response.body));
        await _updateGameIntoDatabase(game);
        return;
      }
    } catch (e) {
      _log.e("error on update request : $e");
      await _updateGameIntoDatabase(game);
    }
  }

  Future<void> handleDeleteRequest(int gameId) async {
    try {
      if (!await checkSyncedWithServerByGameId(gameId)) {
        await _deleteGameIntoDatabase(gameId);
        return;
      }
      final response = await http.delete(
        Uri.parse('$_updateDeleteEndpoint$gameId/'),
        headers: <String, String>{
          'Content-Type': 'application/json; charset=UTF-8',
        },
      );
      if (response.statusCode == 204) {
        _log.d('DELETE request successful: ${response.body}');
        _log.d('updating game into database with synced true');
        await _deleteGameIntoDatabase(gameId);
        return;
      }
    } catch (e) {
      _log.e("");
    }
  }

  Future<void> sendUnsyncedGamesToServer() async {
    try {
      final unsycedGameList = await _gameDatabase.getUnsyncedGames();
      if (unsycedGameList.isEmpty) return;
      final response = await http.post(
        Uri.parse('${_generalEndpoint}bulk_create/'),
        headers: <String, String>{
          'Content-Type': 'application/json; charset=UTF-8',
        },
        body: jsonEncode(unsycedGameList),
      );
      if (response.statusCode == 200) {
        _log.d("succesfully sent unsynced games to server");
      }
    } catch (e) {
      _log.e("error sending unsynced games to server : $e");
    }
  }

  Future<bool> checkSyncedWithServerByGameId(int gameId) async {
    try {
      return await _gameDatabase.getIsSyncedWithServerByGameId(gameId);
    } catch (e) {
      _log.e("");
      rethrow;
    }
  }

  Future<int> _insertGameIntoDatabase(Game game) async {
    try {
      return await _gameDatabase.insertGame(game);
    } catch (e) {
      rethrow;
    }
  }

  Future<void> _deleteGameIntoDatabase(int id) async {
    try {
      await _gameDatabase.deleteGame(id);
    } catch (e) {
      rethrow;
    }
  }

  Future<int> _updateGameIntoDatabase(Game game) async {
    try {
      return await _gameDatabase.updateGame(game);
    } catch (e) {
      rethrow;
    }
  }

  Future<void> _bulkInsertIntoDatabase(List<Game> games) async {
    try {
      await _gameDatabase.bulkInsert(games);
    } catch (e) {
      rethrow;
    }
  }
}
