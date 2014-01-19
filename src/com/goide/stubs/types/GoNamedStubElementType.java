package com.goide.stubs.types;

import com.goide.GoLanguage;
import com.goide.psi.GoNamedElement;
import com.goide.stubs.index.GoAllNamesIndex;
import com.intellij.psi.stubs.IStubElementType;
import com.intellij.psi.stubs.IndexSink;
import com.intellij.psi.stubs.NamedStubBase;
import com.intellij.psi.stubs.StubIndexKey;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.generate.tostring.util.StringUtil;

import java.util.Collection;
import java.util.Collections;

public abstract class GoNamedStubElementType<S extends NamedStubBase<T>, T extends GoNamedElement> extends IStubElementType<S, T> {
  public GoNamedStubElementType(@NonNls @NotNull String debugName) {
    super(debugName, GoLanguage.INSTANCE);
  }

  @NotNull
  public String getExternalId() {
    return "go." + super.toString();
  }

  public void indexStub(@NotNull final S stub, @NotNull final IndexSink sink) {
    String name = stub.getName();
    if (StringUtil.isNotEmpty(name)) {
      //noinspection ConstantConditions
      sink.occurrence(GoAllNamesIndex.ALL_NAMES, name);
      for (StubIndexKey<String, GoNamedElement> key : getExtraIndexKeys()) {
        sink.occurrence(key, name);
      }
    }
  }

  @NotNull
  protected Collection<StubIndexKey<String, GoNamedElement>> getExtraIndexKeys() {
    return Collections.emptyList();
  }
}
