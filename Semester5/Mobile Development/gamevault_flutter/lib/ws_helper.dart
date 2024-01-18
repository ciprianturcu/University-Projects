import 'dart:convert';

import 'package:gamevault_flutter/game.dart';
import 'package:logger/logger.dart';
import 'package:web_socket_channel/io.dart';

class WebSocketHelper {
  final void Function(int) onDelete;
  final void Function(Game) onCreate;
  final void Function(Game) onUpdate;
  final void Function(List<Game>) onBulkCreate;

  WebSocketHelper({
    required this.onDelete,
    required this.onCreate,
    required this.onUpdate,
    required this.onBulkCreate,
  });

  bool _isReconnecting = false;
  static const String _ip = "192.168.100.6:8000";
  late IOWebSocketChannel ws;
  static const _reconnectCooldown = Duration(seconds: 10);
  static final _log = Logger();

  void connectWebSocket() {
    if (_isReconnecting) return;

    _isReconnecting = true;
    try {
      _log.d("trying to connect through the web socket");
      ws = IOWebSocketChannel.connect('ws://$_ip/ws/api/games/');

      ws.stream.listen(
        (data) {
          _log.d("got some data on the websocket");
          final message = json.decode(data);
          handleWebSocketMessage(message);
        },
        onError: (error) {
          _log.e('WebSocket error: $error');
          //_scheduleReconnect();
          ws.sink.close();
        },
        onDone: () {
          _log.d('WebSocket connection closed');
          _scheduleReconnect();
          ws.sink.close();
        },
      );
    } catch (e) {
      _log.e("could not connect web socket");
      _scheduleReconnect();
    }
  }

  void _scheduleReconnect() {
    _isReconnecting = true;
    Future.delayed(_reconnectCooldown, () {
      _isReconnecting = false;
      connectWebSocket();
    });
  }

  Future<void> handleWebSocketMessage(dynamic message) async {
    if (message is Map<String, dynamic>) {
      final action = message['action'];
      final gameData = message['game'];
      if (action != null && gameData != null) {
        switch (action) {
          case 'delete':
            {
              _log.d("delete message received");
              final gameId = gameData['id'];
              onDelete(gameId);
              break;
            }
          case 'create':
            {
              _log.d("create message received : $gameData");
              onCreate(Game.fromMapServerTemplate(gameData));
              break;
            }
          case 'update':
            {
              _log.d("update message received");
              onUpdate(Game.fromMapServerTemplate(gameData));
              break;
            }
          case 'bulk_create':
            {
              _log.d("bulk create message received");
              List<Game> gameList = Game.parseGames(gameData);
              onBulkCreate(gameList);
              break;
            }
          default:
            _log.e('Unknown action: $action');
        }
      }
    }
  }
}
