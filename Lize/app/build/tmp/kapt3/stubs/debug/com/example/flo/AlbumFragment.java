package com.example.flo;

import java.lang.System;

@kotlin.Metadata(mv = {1, 6, 0}, k = 1, d1 = {"\u0000Z\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\u0010\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u0013H\u0002J\b\u0010\u0014\u001a\u00020\u0013H\u0002J\u0010\u0010\u0015\u001a\u00020\u000f2\u0006\u0010\u0012\u001a\u00020\u0013H\u0002J\u0018\u0010\u0016\u001a\u00020\u00112\u0006\u0010\u0017\u001a\u00020\u00132\u0006\u0010\u0012\u001a\u00020\u0013H\u0002J&\u0010\u0018\u001a\u0004\u0018\u00010\u00192\u0006\u0010\u001a\u001a\u00020\u001b2\b\u0010\u001c\u001a\u0004\u0018\u00010\u001d2\b\u0010\u001e\u001a\u0004\u0018\u00010\u001fH\u0016J\u0010\u0010 \u001a\u00020\u00112\u0006\u0010!\u001a\u00020\"H\u0002J\u0010\u0010#\u001a\u00020\u00112\u0006\u0010!\u001a\u00020\"H\u0002R\u001a\u0010\u0003\u001a\u00020\u0004X\u0086.\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\bR\u000e\u0010\t\u001a\u00020\nX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\r0\fX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\u000fX\u0082\u000e\u00a2\u0006\u0002\n\u0000\u00a8\u0006$"}, d2 = {"Lcom/example/flo/AlbumFragment;", "Landroidx/fragment/app/Fragment;", "()V", "binding", "Lcom/example/flo/databinding/FragmentAlbumBinding;", "getBinding", "()Lcom/example/flo/databinding/FragmentAlbumBinding;", "setBinding", "(Lcom/example/flo/databinding/FragmentAlbumBinding;)V", "gson", "Lcom/google/gson/Gson;", "information", "Ljava/util/ArrayList;", "", "isLiked", "", "disLikedAlbum", "", "albumId", "", "getJwt", "isLikedAlbum", "likealbum", "userId", "onCreateView", "Landroid/view/View;", "inflater", "Landroid/view/LayoutInflater;", "container", "Landroid/view/ViewGroup;", "savedInstanceState", "Landroid/os/Bundle;", "setInit", "album", "Lcom/example/flo/Album;", "setOnClickListener", "app_debug"})
public final class AlbumFragment extends androidx.fragment.app.Fragment {
    public com.example.flo.databinding.FragmentAlbumBinding binding;
    private final java.util.ArrayList<java.lang.String> information = null;
    private com.google.gson.Gson gson;
    private boolean isLiked = false;
    
    public AlbumFragment() {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.example.flo.databinding.FragmentAlbumBinding getBinding() {
        return null;
    }
    
    public final void setBinding(@org.jetbrains.annotations.NotNull()
    com.example.flo.databinding.FragmentAlbumBinding p0) {
    }
    
    @org.jetbrains.annotations.Nullable()
    @java.lang.Override()
    public android.view.View onCreateView(@org.jetbrains.annotations.NotNull()
    android.view.LayoutInflater inflater, @org.jetbrains.annotations.Nullable()
    android.view.ViewGroup container, @org.jetbrains.annotations.Nullable()
    android.os.Bundle savedInstanceState) {
        return null;
    }
    
    private final void setInit(com.example.flo.Album album) {
    }
    
    private final int getJwt() {
        return 0;
    }
    
    private final void likealbum(int userId, int albumId) {
    }
    
    private final boolean isLikedAlbum(int albumId) {
        return false;
    }
    
    private final void disLikedAlbum(int albumId) {
    }
    
    private final void setOnClickListener(com.example.flo.Album album) {
    }
}