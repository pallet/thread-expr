# thread-expr Release Notes

The latest release is 1.2.0.

## 1.2.0

- Add indentation metadata to macros
  This ensures the macros indent properly (at least in ritz).

- make 1.3 compatible while maintaining 1.2 backwards compatibility

## 1.1.0

- Fixed bug with duplicated apply-map->.

- Added ->> macros
  These are last argument threading macros.

- Added `require` dependency for symbol macros. Added documentation and more
  bindings to `-->`; all are now supported, except for `apply-map` and
  `let-with-arg`.

- added -->; similar to ->, but uses symbol macro-let to bind many of the
  thread-expr functions.


## 1.0.0

This is the initial standalone release.  The library has been extracted
from the main [pallet repository](https://github.com/pallet/pallet).
