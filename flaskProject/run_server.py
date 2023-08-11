from tornado.httpserver import HTTPServer
from tornado.ioloop import IOLoop
from tornado.wsgi import WSGIContainer

from app import app

s = HTTPServer(WSGIContainer(app))
s.listen(5000)  # 监听5000端口
IOLoop.current().start()
