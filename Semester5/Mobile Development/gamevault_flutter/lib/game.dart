import 'dart:convert';

class Game {
  int? id;
  String title;
  String description;
  String genre;
  double progress;
  double rating;
  int hoursPlayed;
  int isSyncedWithServer;

  Game({
    this.id,
    required this.title,
    required this.description,
    required this.genre,
    required this.progress,
    required this.rating,
    required this.hoursPlayed,
    required this.isSyncedWithServer,
  });

  Map<String, dynamic> toMap() {
    return {
      'id': id,
      'title': title,
      'description': description,
      'genre': genre,
      'progress': progress,
      'rating': rating,
      'hoursPlayed': hoursPlayed,
      'isSyncedWithServer': isSyncedWithServer,
    };
  }

  Map<String, dynamic> toMapPublicDetails() {
    return {
      'title': title,
      'description': description,
      'genre': genre,
      'progress': progress,
      'rating': rating,
      'hoursPlayed': hoursPlayed,
    };
  }

  factory Game.fromMap(Map<String, dynamic> map) {
    return Game(
      id: map['id'],
      title: map['title'],
      description: map['description'],
      genre: map['genre'],
      progress: map['progress'],
      rating: map['rating'],
      hoursPlayed: map['hoursPlayed'],
      isSyncedWithServer: map['isSyncedWithServer'],
    );
  }

  factory Game.fromMapServerTemplate(Map<String, dynamic> map) {
    return Game(
      id: map['id'],
      title: map['title'],
      description: map['description'],
      genre: map['genre'],
      progress: map['progress'],
      rating: map['rating'],
      hoursPlayed: map['hoursPlayed'],
      isSyncedWithServer: 1,
    );
  }

  static List<Game> parseGames(String responseBody) {
    final parsed = jsonDecode(responseBody).cast<Map<String, dynamic>>();
    return parsed
        .map<Game>((json) => Game.fromMapServerTemplate(json))
        .toList();
  }

  @override
  bool operator ==(Object other) {
    if (identical(this, other)) return true;

    return other is Game &&
        id == other.id &&
        title == other.title &&
        description == other.description &&
        genre == other.genre &&
        progress == other.progress &&
        rating == other.rating &&
        hoursPlayed == other.hoursPlayed &&
        isSyncedWithServer == other.isSyncedWithServer;
  }

  @override
  int get hashCode {
    return id.hashCode ^
        title.hashCode ^
        description.hashCode ^
        genre.hashCode ^
        progress.hashCode ^
        rating.hashCode ^
        hoursPlayed.hashCode ^
        isSyncedWithServer.hashCode;
  }

  bool get syncedWithServer {
    if (isSyncedWithServer == 1) {
      return true;
    }
    return false;
  }
}
