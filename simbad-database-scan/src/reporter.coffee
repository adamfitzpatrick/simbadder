term = require("terminal-kit").terminal
fs = require "fs"

class Reporter
  rotorLines = []
  lines = []
  currentLine = null

  constructor: (initLines) ->
    initLines = initLines or []
    term.reset()
    term.hideCursor()
    process.stdout.write ""
    lines.push ""
    @setLine i, line for line, i in initLines

  toTop = ->
    term.previousLine currentLine if currentLine > 0
    currentLine = 0

  toBottom = ->
    term.nextLine lines.length - currentLine - 1 if currentLine isnt lines.length - 1
    currentLine = lines.length - 1

  toLine = (line) ->
    return toBottom() if line >= lines.length
    return toTop() if line < 0
    term.nextLine line - currentLine if line > currentLine
    term.previousLine currentLine - line if currentLine > line
    currentLine = line

  addLine = ->
    previouslyCurrentLine = currentLine
    toBottom()
    process.stdout.write "\n"
    lines.push ""
    currentLine++
    toLine previouslyCurrentLine

  getProgressBar: (length, progress) ->
    barString = "\u2563"
    barString += "\u2588" for i in [0...progress] if progress > 0
    barString += "\u2591" for i in [progress...length] if progress < length
    barString += "\u2560"

  setLine: (line, content) ->
    addLine() for newLine in [0..lines.length - line] if line >= lines.length
    lines[line] = { content: content, line: line, write: => @writeContent line }
    @writeContent line
    lines[line]

  writeContent: (line) ->
    toLine line
    term.eraseLine()
    term.column 0
    process.stdout.write lines[line].content

  finish: ->
    toBottom()
    process.stdout.write "\x1B[?25h\n"

module.exports = Reporter
