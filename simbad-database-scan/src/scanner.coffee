http = require "http"
_ = require "lodash"

REQUEST_OPTIONS =
  hostname: "localhost"
  port: 7901
  path: "/sample"
  method: "POST"
  headers:
    "Content-Type": "application/json"

class Scanner
  constructor: (@coords, @resultDb, @writeStatus) ->
    @resolved = false

  generateQuery: ->
    JSON.stringify
      ra:
        "$gt": @coords.ra.min
        "$lte": @coords.ra.max
      dec:
        "$gt": @coords.dec.min
        "$lte": @coords.dec.max

  scan: (callback) =>
    requestBody = @generateQuery()
    request = http.request REQUEST_OPTIONS, (response) =>
      body = ""
      response.on "data", (chunk) -> body += chunk
      response.on "end", =>
        try
          body = JSON.parse body
          @writeResult { status: body.status, reason: body.reason }, callback
        catch error
          @writeResult { status: 500, reason: error }, callback
    request.write requestBody
    request.end()

    request.on "error", (err) =>
      @writeResult { status: 500, reason: err }, callback

  writeResult: (result, callback) =>
    result.status = result.status or 200
    @writeStatus result.status isnt 200
    result.reason = result.reason or ""
    result.ra = @coords.ra
    result.dec = @coords.dec
    promise = @resultDb.insert result
    promise.on "complete", =>
      @resolved = true
      callback()

module.exports = Scanner