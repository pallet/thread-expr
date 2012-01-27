(ns pallet.thread-expr
  "Macros that can be used in an expression thread."
  (:require [clojure.tools.macro :as macro]))

(letfn [(for- [threader arg seq-exprs body]
          `(reduce #(%2 %1)
                   ~arg
                   (for ~seq-exprs
                     (fn [arg#] (~threader arg# ~@body)))))]
  (defmacro for->
    "Apply a thread expression to a sequence.
   eg.
      (-> 1
        (for-> [x [1 2 3]]
          (+ x)))
   => 7"
    [arg seq-exprs & body]
    (for- 'clojure.core/-> arg seq-exprs body))

  (defmacro for->>
    "Apply a thread expression to a sequence.
   eg.
      (->> 1
         (for->> [x [1 2 3]]
           (+ x)))
   => 7"
    [seq-exprs & body]
    (let [arg (last body)
          body (butlast body)]
      (for- 'clojure.core/->> arg seq-exprs body))))

(letfn [(when- [threader arg condition body]
          `(let [arg# ~arg]
             (if ~condition
               (~threader arg# ~@body)
               arg#)))]

  (defmacro when->
    "A `when` form that can appear in a request thread.
   eg.
      (-> 1
        (when-> true
          (+ 1)))
   => 2"
    [arg condition & body]
    (when- 'clojure.core/-> arg condition body))

  (defmacro when->>
    "A `when` form that can appear in a request thread.
   eg.
      (->> 1
         (when->> true
           (+ 1)))
   => 2"
    [condition & body]
    (let [arg (last body)
          body (butlast body)]
      (when- 'clojure.core/->> arg condition body))))

(letfn [(when-not-
          [threader arg condition body]
          `(let [arg# ~arg]
             (if-not ~condition
               (~threader arg# ~@body)
               arg#)))]

  (defmacro when-not->
    "A `when-not` form that can appear in a request thread.
   eg.
      (-> 1
        (when-not-> true
          (+ 1)))
   => 1"
    [arg condition & body]
    (when-not- 'clojure.core/-> arg condition body))

  (defmacro when-not->>
    "A `when-not` form that can appear in a request thread.
   eg.
      (->> 1
         (when-not->> true
           (+ 1)))
   => 1"
    [condition & body]
    (let [arg (last body)
          body (butlast body)]
      (when-not- 'clojure.core/->> arg condition body))))

(letfn [(let-
          [threader arg binding body]
          `(let ~binding
             (~threader ~arg ~@body)))]

  (defmacro let->
    "A `let` form that can appear in a request thread.
   eg.
      (-> 1
        (let-> [a 1]
          (+ a)))
   => 2"
    [arg binding & body]
    (let- 'clojure.core/-> arg binding body))

  (defmacro let->>
    "A `let` form that can appear in a request thread.
   eg.
      (->> 1
         (let->> [a 1]
           (+ a)))
   => 2"
    [binding & body]
    (let [arg (last body)
          body (butlast body)]
      (let- 'clojure.core/->> arg binding body))))

(letfn [(binding-
          [threader arg bindings body]
          `(binding ~bindings
             (~threader ~arg ~@body)))]

  (defmacro binding->
    "A `binding` form that can appear in a request thread.
   eg.
      (def *a* 0)
      (-> 1
        (binding-> [*a* 1]
          (+ *a*)))
   => 2"
    [arg bindings & body]
    (binding- 'clojure.core/-> arg bindings body))

  (defmacro binding->>
    "A `binding` form that can appear in a request thread.
   eg.
      (def *a* 0)
      (->> 1
         (binding->> [*a* 1]
           (+ *a*)))
   => 2"
    [bindings & body]
    (let [arg (last body)
          body (butlast body)]
      (binding- 'clojure.core/->> arg bindings body))))

(letfn [(when-let-
          [threader arg binding body]
          `(let [arg# ~arg]
             (if-let ~binding
               (~threader arg# ~@body)
               arg#)))]

  (defmacro when-let->
    "A `when-let` form that can appear in a request thread.
   eg.
      (-> 1
        (when-let-> [a 1]
          (+ a)))
   => 2"
    [arg binding & body]
    (when-let- 'clojure.core/-> arg binding body))

  (defmacro when-let->>
    "A `when-let` form that can appear in a request thread.
   eg.
      (->> 1
         (when-let->> [a 1]
           (+ a)))
   => 2"
    [binding & body]
    (let [arg (last body)
          body (butlast body)]
      (when-let- 'clojure.core/->> arg binding body))))

(letfn [(if-
          ([threader arg condition form]
             `(let [arg# ~arg]
                (if ~condition
                  (~threader arg# ~form)
                  arg#)))
          ([threader arg condition form else-form]
             `(let [arg# ~arg]
                (if ~condition
                  (~threader arg# ~form)
                  (~threader arg# ~else-form)))))]

  (defmacro if->
    "An `if` form that can appear in a request thread
   eg.
      (-> 1
        (if-> true
          (+ 1)
          (+ 2)))
   => 2"
    ([arg condition form]
       (if- 'clojure.core/-> arg condition form))
    ([arg condition form else-form]
       (if- 'clojure.core/-> arg condition form else-form)))

  (defmacro if->>
    "An `if` form that can appear in a request thread
   eg.
      (->> 1
         (if->> true
           (+ 1)
           (+ 2)))
   => 2"
    ([condition form arg]
       (if- 'clojure.core/->> arg condition form))
    ([condition form else-form arg]
       (if- 'clojure.core/->> arg condition form else-form))))

(letfn [(if-not-
          ([threader arg condition form]
             `(let [arg# ~arg]
                (if-not ~condition
                  (~threader arg# ~form)
                  arg#)))
          ([threader arg condition form else-form]
             `(let [arg# ~arg]
                (if-not ~condition
                  (~threader arg# ~form)
                  (~threader arg# ~else-form)))))]

  (defmacro if-not->
    "An `if-not` form that can appear in a request thread
   eg.
      (-> 1
        (if-not-> true
          (+ 1)
          (+ 2)))
   => 3"
    ([arg condition form]
       (if-not- 'clojure.core/-> arg condition form))
    ([arg condition form else-form]
       (if-not- 'clojure.core/-> arg condition form else-form)))

  (defmacro if-not->>
    "An `if-not` form that can appear in a request thread
   eg.
      (->> 1
         (if-not->> true
           (+ 1)
           (+ 2)))
   => 3"
    ([condition form arg]
       (if-not- 'clojure.core/->> arg condition form))
    ([condition form else-form arg]
       (if-not- 'clojure.core/->> arg condition form else-form))))

(letfn [(arg-
          [threader arg sym body]
          `(let [~sym ~arg]
             (~threader ~sym ~@body)))]

  (defmacro arg->
    "Lexically assign the threaded argument to the specified symbol.

       (-> 1
         (arg-> [x] (+ x)))

       => 2"
    [arg [sym] & body]
    (arg- 'clojure.core/-> arg sym body))

  (defmacro arg->>
    "Lexically assign the threaded argument to the specified symbol.

       (->> 1
          (arg->> [x] (+ x)))

       => 2"
    [[sym] & body]
    (let [arg (last body)
          body (butlast body)]
      (arg- 'clojure.core/->> arg sym body))))

(letfn [(let-with-arg-
          [threader arg arg-symbol binding body]
          `(let [~arg-symbol ~arg]
             (let ~binding
               (~threader ~arg-symbol ~@body))))]

  (defmacro let-with-arg->
    "A `let` form that can appear in a request thread, and assign the
   value of the threaded arg.

   eg.
      (-> 1
        (let-with-arg-> val [a 1]
          (+ a val)))
   => 3"
    [arg arg-symbol binding & body]
    (let-with-arg- 'clojure.core/-> arg arg-symbol binding body))

  (defmacro let-with-arg->>
    "A `let` form that can appear in a request thread, and assign the
   value of the threaded arg.

   eg.
      (->> 1
         (let-with-arg->> val [a 1]
           (+ a val)))
   => 3"
    [arg-symbol binding & body]
    (let [arg (last body)
          body (butlast body)]
      (let-with-arg- 'clojure.core/->> arg arg-symbol binding body))))

(letfn [(apply-
          [arg f arg-coll]
          `(let [arg# ~arg]
             (apply ~f arg# ~@arg-coll)))]

  (defmacro apply->
    "Apply in a threaded expression.
   e.g.
      (-> 1
        (apply-> + [1 2 3]))
   => 7"
    [arg f & arg-coll]
    (apply- arg f arg-coll))

  (defmacro apply->>
    "Apply in a threaded expression.
   e.g.
      (->> 1
         (apply->> + [1 2 3]))
   => 7"
    [f & arg-coll]
    (let [arg (last arg-coll)
          arg-coll (butlast arg-coll)]
      (apply- arg f arg-coll))))

(letfn [(apply-map-
          [arg f arg-coll]
          `(let [arg# ~arg]
             (apply ~f arg#
                    ~@(butlast arg-coll)
                    (apply concat ~(last arg-coll)))))]

  (defmacro apply-map->
    "Apply in a threaded expression.
   e.g.
      (-> :a
        (apply-map-> hash-map 1 {:b 2}))
   => {:a 1 :b 2}"
    [arg f & arg-coll]
    (apply-map- arg f arg-coll))

  (defmacro apply-map->>
    "Apply in a threaded expression.
   e.g.
      (->> :a
         (apply-map->> hash-map 1 {:b 2}))
   => {:a 1 :b 2}"
    [f & arg-coll]
    (let [arg (last arg-coll)
          arg-coll (butlast arg-coll)]
      (apply-map- arg f arg-coll))))

(defmacro -->
  "Similar to `clojure.core/->`, with added symbol macros for the
  various threading macros found in `pallet.thread-expr`. Currently
  supported symbol macros are:

* binding
* for
* let
* when, when-not, when-let
* if, if-not
* apply
* expose-request-as (bound to `pallet.thread-expr/arg->`

    Examples:

     (--> 5
        (let [y 1]
          (for [x (range 3)]
            (+ x y)))
        (+ 1))
 => 12

      (--> 5
           (expose-request-as [x] (+ x))
           (+ 1))
 => 11"
  [& forms]
  `(macro/symbol-macrolet
    [~'binding binding->
     ~'for for->
     ~'if if->
     ~'if-not if-not->
     ~'when when->
     ~'when-not when-not->
     ~'when-let when-let->
     ~'let let->
     ~'apply apply->
     ~'expose-request-as arg->]
    (-> ~@forms)))

(defmacro -->>
  "Similar to `clojure.core/->>`, with added symbol macros for the
  various threading macros found in `pallet.thread-expr`. Currently
  supported symbol macros are:

* binding
* for
* let
* when, when-not, when-let
* if, if-not
* apply
* expose-request-as (bound to `pallet.thread-expr/arg->>`

    Examples:

     (-->> 5
         (let [y 1]
           (for [x (range 3)]
             (+ x y)))
         (+ 1))
 => 12

      (-->> 5
            (expose-request-as [x] (+ x))
            (+ 1))
 => 11"
  [& forms]
  `(macro/symbol-macrolet
    [~'binding binding->>
     ~'for for->>
     ~'if if->>
     ~'if-not if-not->>
     ~'when when->>
     ~'when-not when-not->>
     ~'when-let when-let->>
     ~'let let->>
     ~'apply apply->>
     ~'expose-request-as arg->>]
    (->> ~@forms)))
